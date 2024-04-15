package com.fiap.challenge.targetcustomer.model.dto;

import com.fiap.challenge.targetcustomer.model.TelefoneEmpresa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TelefoneEmpresaDTO {

    @NotNull
    private Long idCadastro;

    @NotNull
    private Long ddi;

    @NotNull
    private Long ddd;

    @NotNull
    private Long telefone;

    @NotNull
    @Size(max = 20)
    private String tipoTelefone;

    public static TelefoneEmpresa toTelefoneEmpresa(TelefoneEmpresaDTO dto) {
        var telefoneEmpresa = new TelefoneEmpresa();

        telefoneEmpresa.setDdi(dto.getDdi());
        telefoneEmpresa.setDdd(dto.getDdd());
        telefoneEmpresa.setTelefone(dto.getTelefone());
        telefoneEmpresa.setTipoTelefone(dto.getTipoTelefone());

        return telefoneEmpresa;
    }
}
