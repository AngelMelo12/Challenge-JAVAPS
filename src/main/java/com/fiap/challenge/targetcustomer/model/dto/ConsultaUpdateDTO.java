package com.fiap.challenge.targetcustomer.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConsultaUpdateDTO(
        @NotNull @Size(min = 5, message = "Descrição deve ser maior que 5 caracteres") String descricao
) {
}
