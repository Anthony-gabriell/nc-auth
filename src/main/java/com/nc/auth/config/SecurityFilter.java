package com.nc.auth.config;

import com.nc.auth.repository.CitizenRepository;
import com.nc.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// registra esta classe como bean do spring, é isso que permite injetá-la no SecurityConfig
@Component
public class SecurityFilter extends OncePerRequestFilter {

    // formato padrão do header é Authorization: Bearer seguido do token, o espaço no final faz parte do prefixo
    private static final String PREFIXO = "Bearer ";

    // final para garantir que a dependência não mude após ser injetada
    private final TokenService tokenService; // valida o token e devolve o username que está dentro dele
    private final CitizenRepository citizenRepository;

    // injeção de dependência via construtor, o spring entrega os dois objetos prontos
    public SecurityFilter(TokenService tokenService, CitizenRepository citizenRepository) {
        this.tokenService = tokenService;
        this.citizenRepository = citizenRepository;
    }

    @Override
    // é aqui que fica a lógica do nosso filtro, ele roda para cada requisição que chega na api
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // lê o header Authorization da requisição, se ele não existir o resultado é null
        String cabecalho = request.getHeader(HttpHeaders.AUTHORIZATION);

        // só tenta autenticar se houver Authorization: Bearer seguido do token
        // a checagem de null vem primeiro para evitar NullPointerException no startsWith
        if (cabecalho != null && cabecalho.startsWith(PREFIXO)) {
            // remove o prefixo Bearer e o espaço, sobrando só o token puro
            String token = cabecalho.substring(PREFIXO.length());
            // se o token for inválido, adulterado ou expirado, o optional vem vazio e nada acontece
            // o ifPresent só executa a lambda quando existe valor, ou seja, quando o token é válido
            tokenService.validarToken(token)
                    .ifPresent(username -> autenticar(username, request));
        }


        filterChain.doFilter(request, response);
    }


    // recebe o username já validado e a requisição, para registrar o usuário como autenticado
    private void autenticar(String username, HttpServletRequest request) {
        // se já existe autenticação no contexto, não faz nada e evita sobrescrever
        // o contexto é guardado por thread, então cada requisição começa com o seu próprio contexto
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        // busca o cidadão no banco de dados usando o repositório
        // orElse null devolve null quando o optional está vazio, ou seja, quando o cidadão não existe
        UserDetails usuario = citizenRepository.findCitizenByNome(username)
                .orElse(null);

        // se não encontrar ou não estiver ativo, aborta a autenticação
        // isEnabled é o mtodo do UserDetails que indica se a conta está habilitada
        if (usuario == null || !usuario.isEnabled()) {
            return;
        }

        // cria o objeto Authentication que representa o usuário logado
        // var deixa o compilador inferir o tipo, que aqui é UsernamePasswordAuthenticationToken
        var autenticacao = new UsernamePasswordAuthenticationToken(
                usuario, null, usuario.getAuthorities());
        // anexa detalhes da requisição, como ip de origem e id de sessão, útil para auditoria e logs
        autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // cria um contexto novo e vazio em vez de alterar o que já existe
        SecurityContext contexto = SecurityContextHolder.createEmptyContext();
        // coloca a autenticação dentro do contexto
        contexto.setAuthentication(autenticacao);
        // registra o contexto na thread atual
        SecurityContextHolder.setContext(contexto);
    }
}