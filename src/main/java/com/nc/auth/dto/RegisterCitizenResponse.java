package com.nc.auth.dto;

import com.nc.auth.models.CitizenClass;

public record RegisterCitizenResponse(String nome, String user, CitizenClass classe) {
}
