package com.nc.auth.service;

import com.nc.auth.config.JwtProperties;
import com.nc.auth.models.Citizen; // Sua entidade!
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    private final JwtProperties props;
    private final SecretKey chave;

    public TokenService(JwtProperties props) {
        this.props = props;
        // decodifica o base64 e cria a chave hmac do Spring
        this.chave = Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.secret()));
    }

    // gera o access token curto
    public String gerarToken(Citizen citizen) {
        Instant agora = Instant.now();
        return Jwts.builder()
                .issuer(props.issuer())
                .subject(citizen.getUsername()) // Pega o nome do cidadão (que você mapeou no UserDetails)
                .claim("roles", citizen.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plus(props.accessExpiration())))
                .signWith(chave)
                .compact();
    }

    // valida o token e devolve o username subject
    public Optional<String> validarToken(String token) {
        try {
            return Optional.ofNullable(lerClaims(token).getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Claims lerClaims(String token) {
        return Jwts.parser()
                .verifyWith(chave)
                .requireIssuer(props.issuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long segundosDeValidade() {
        return props.accessExpiration().toSeconds();
    }
}