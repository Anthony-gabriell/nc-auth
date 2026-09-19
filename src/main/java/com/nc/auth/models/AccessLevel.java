package com.nc.auth.models;

public enum AccessLevel {
    S("S"),
    A("A"),
    B("B");

    public final String accessLevel;

    AccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getAccessLevel() {
        return accessLevel;
    }
}
