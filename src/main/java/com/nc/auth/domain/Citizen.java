package com.nc.auth.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity // define que essa classe sera uma tabela no banco de dados
public class Citizen {

    @Id // define a chave primaria
    @GeneratedValue //UUID
    private String cityId;
    private String nome;
    private String password;
    private AccessLevel acessLevel;
    private CitizenClass classe;
    private Long edcoinBalance;
    private Date creatAt;
    private Boolean activr;



}
