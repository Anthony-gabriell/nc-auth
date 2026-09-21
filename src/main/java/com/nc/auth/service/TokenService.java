package com.nc.auth.service;

import com.nc.auth.config.JwtProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;


@Service
public class TokenService {

        private final JwtProperties props;
        private final SecretKey chave;

        public TokenService(JwtProperties props) {
            this.props = props;
            // Decodifica o Base64 e cria a chave HMAC (lança WeakKeyException se < 256 bits)
            this.chave = Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.secret()));
        }

        /** Gera o access token (curto). */
        public String gerarToken(Usuario usuario) {
            Instant agora = Instant.now();
            return Jwts.builder()
                    .issuer(props.issuer())
                    .subject(usuario.getUsername())
                    .claim("roles", usuario.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList())
                    .issuedAt(Date.from(agora))
                    .expiration(Date.from(agora.plus(props.accessExpiration())))
                    .signWith(chave)   // o algoritmo (HS256) é inferido pelo tamanho da chave
                    .compact();
        }

        /**
         * Valida o token e devolve o username (subject).
         * Assinatura, expiração e issuer são checados no parse.
         * Se qualquer coisa falhar, devolve vazio, sem lançar exceção para o chamador.
         */
        public Optional<String> validarToken(String token) {
            try {
                return Optional.ofNullable(lerClaims(token).getSubject());
            } catch (JwtException | IllegalArgumentException e) {
                // ExpiredJwtException, SignatureException, MalformedJwtException... todas herdam de JwtException
                return Optional.empty();
            }
        }

        private Claims lerClaims(String token) {
            return Jwts.parser()
                    .verifyWith(chave)
                    .requireIssuer(props.issuer())
                    .build()
                    .parseSignedClaims(token)   // "Signed" rejeita tokens sem assinatura (alg: none)
                    .getPayload();
        }

        public long segundosDeValidade() {
            return props.accessExpiration().toSeconds();
        }
}

