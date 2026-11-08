package com.nc.auth.models;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class Citizen implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCitizen;
    private String cityId;
    private String nome;
    @Column(name = "username", unique = true, nullable = false)
    private String user;
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
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (this.accessLevel != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.accessLevel.name()));
        }
        if (this.classe != null) {
            authorities.add(new SimpleGrantedAuthority("CLASS_" + this.classe.name()));
        }
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.user;
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
        return Boolean.TRUE.equals(this.active);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setIdCitizen(Long idCitizen) {
        this.idCitizen = idCitizen;
    }

    public Long getIdCitizen() {
        return this.idCitizen;
    }

    public void setClasse(CitizenClass classe) {
        this.classe = classe;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public BigDecimal getEdCoin() {
        return edCoin;
    }

    public void setEdCoin(BigDecimal edCoin) {
        this.edCoin = edCoin;
    }

    public Date getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(Date creatAt) {
        this.creatAt = creatAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
