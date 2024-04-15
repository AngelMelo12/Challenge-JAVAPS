package com.fiap.challenge.targetcustomer.model.dto;

import com.fiap.challenge.targetcustomer.model.EnderecoEmpresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnderecoEmpresaDTO {

    @NotNull
    private Long idCadastro;

    @NotNull
    private Long logradouro;

    @NotNull
    private Long cep;

    @NotBlank
    private String descricaoPontoDeReferencia;

    public static EnderecoEmpresa toEnderecoEmpresa(EnderecoEmpresaDTO dto) {
        var enderecoEmpresa = new EnderecoEmpresa();

        enderecoEmpresa.setLogradouro(dto.getLogradouro());
        enderecoEmpresa.setCep(dto.getCep());
        enderecoEmpresa.setDescricaoPontoDeReferencia(dto.getDescricaoPontoDeReferencia());

        return enderecoEmpresa;
    }
}
