package com.fiap.challenge.targetcustomer.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroUpdateDTO(
        @NotNull Long cnpj,
        @NotNull @Size(min = 5, max = 60, message = "Senha deve ter no mínimo 5 caracteres e no máximo 60 caracteres") String senha,

        @NotBlank @Size(min = 5, max = 80) String razaoSocial
) {
}
