package com.nc.auth.dto;

import com.nc.auth.models.CitizenClass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(@NotBlank(message = "Nome não pode ser vazio") String nome,
                              @NotBlank(message = "Senha não pode ser vazia") String password,
                              @NotNull(message = "Classe é obrigatória") CitizenClass classe) {
}
