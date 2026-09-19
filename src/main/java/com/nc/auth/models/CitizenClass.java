package com.nc.auth.models;

public enum CitizenClass {
    MARGINAL("MARGINAL"),
    CORPORATE("CORPORATE"),
    NOMAD("NOMAD");

    public final String citizenClass;

    CitizenClass(String citizenClass){
        this.citizenClass = citizenClass;
    }

    public String getCitizenClass() {
        return citizenClass;
    }
}
