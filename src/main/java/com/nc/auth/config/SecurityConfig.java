package com.nc.auth.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration // indica que essa classe tem um bean de configuracao
@EnableWebSecurity // ativa a seguranca web e permite que criacao das regras de acesso
public class SecurityConfig {

}
