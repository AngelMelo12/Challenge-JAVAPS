package com.fiap.challenge.targetcustomer.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TelefoneEmpresaUpdateDTO(
        @NotNull Long ddi,
        @NotNull Long ddd,
        @NotNull Long telefone,
        @NotNull @Size(max = 20) String tipoTelefone
) {
}
