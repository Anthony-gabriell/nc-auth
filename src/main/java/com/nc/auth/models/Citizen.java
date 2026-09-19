package com.nc.auth.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity // define que essa classe sera uma tabela no bancod de dados
public class Citizen {

    @Id // define a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //UUID o DBA gera o id auto.
    private Long idCitizen;
    private String cityId;
    private String nome;
    private String password;
    private AccessLevel acessLevel;
    private CitizenClass classe;
    private BigDecimal edCoin;
    private Date creatAt;
    private Boolean active;



}
