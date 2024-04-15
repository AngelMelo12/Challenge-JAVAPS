package com.fiap.challenge.targetcustomer.model.dto;

import com.fiap.challenge.targetcustomer.model.EmailEmpresa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailEmpresaDTO {

    @NotNull
    private Long idCadastro;
    @Email
    private String email;

    public static EmailEmpresa toEmailEmpresa(EmailEmpresaDTO dto) {
        var emailEmpresa = new EmailEmpresa();

        emailEmpresa.setEmail(dto.getEmail());

        return emailEmpresa;
    }
}
