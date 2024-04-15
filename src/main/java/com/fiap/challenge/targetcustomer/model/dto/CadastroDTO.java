package com.fiap.challenge.targetcustomer.model.dto;

import com.fiap.challenge.targetcustomer.model.Cadastro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CadastroDTO {

    @NotNull
    private Long cnpj;

    @NotBlank
    @Size(min = 5, max = 60, message = "Senha deve ter no mínimo 5 caracteres e no máximo 60 caracteres")
    private String senha;

    @NotBlank
    @Size(min = 5, max = 80)
    private String razaoSocial;

    public static Cadastro toCadastro(CadastroDTO dto) {
        var cadastro = new Cadastro();

        cadastro.setCnpj(dto.getCnpj());
        cadastro.setSenha(dto.getSenha());
        cadastro.setRazaoSocial(dto.getRazaoSocial());
        cadastro.setEnderecosEmpresas(new ArrayList<>());
        cadastro.setEmailEmpresas(new ArrayList<>());
        cadastro.setTelefonesEmpresas(new ArrayList<>());

        return cadastro;
    }
}
