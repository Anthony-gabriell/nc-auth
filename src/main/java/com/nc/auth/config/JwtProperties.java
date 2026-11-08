package com.nc.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.Duration;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        String secret,
        String issuer,
        Duration accessExpiration,   // o Spring converte "15m" em Duration
        Duration refreshExpiration   // e "7d" também
) {}
