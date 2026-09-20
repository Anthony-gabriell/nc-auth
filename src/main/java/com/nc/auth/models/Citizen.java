package com.nc.auth.models;


import jakarta.persistence.*;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity // define que essa classe sera uma tabela no bancod de dados
public class Citizen implements UserDetails {

    @Id // define a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //UUID o DBA gera o id auto.
    private Long idCitizen;
    private String cityId;
    private String nome;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    @Enumerated(EnumType.STRING)
    private CitizenClass classe;
    private BigDecimal edCoin;
    private Date creatAt;
    private Boolean active;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.nome;
    }

    public CitizenClass getClasse() {
        return classe;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setIdCitizen(Long idCitizen) {
        this.idCitizen = idCitizen;
    }

    public void setClasse(CitizenClass classe) {
        this.classe = classe;
    }
}
