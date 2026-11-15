package com.nc.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = "Usuário não pode ser vazio") String user,
                           @NotBlank(message = "Senha não pode ser vazia") String password) {
}
