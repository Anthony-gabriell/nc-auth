package com.nc.auth.models;

public enum CitizenClass {
    MARGINAL("MARGINAL"),
    CORPORATE("CORPORATE"),
    NOMAD("NOMAD");

    public final String citizenClass;

    // injeçãao de dependecia via construtor
    CitizenClass(String citizenClass){
        this.citizenClass = citizenClass;
    }

    public String getCitizenClass() {
        return citizenClass;
    }
}
