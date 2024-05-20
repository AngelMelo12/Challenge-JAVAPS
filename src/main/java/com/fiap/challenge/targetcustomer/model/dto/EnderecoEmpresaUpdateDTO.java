package com.fiap.challenge.targetcustomer.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoEmpresaUpdateDTO(
        @NotNull Long logradouro,
        @NotNull Long cep,
        @NotBlank String descricaoPontoDeReferencia
) {
}
