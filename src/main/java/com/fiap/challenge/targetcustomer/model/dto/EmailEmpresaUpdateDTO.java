package com.fiap.challenge.targetcustomer.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailEmpresaUpdateDTO(
        @NotNull @Email(message = "E-mail deve estar em formatação válida") String email
) {
}
