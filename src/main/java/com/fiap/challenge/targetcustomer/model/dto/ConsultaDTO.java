package com.fiap.challenge.targetcustomer.model.dto;

import com.fiap.challenge.targetcustomer.model.Consulta;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaDTO {

    @NotNull
    private Long idCadastro;

    @NotNull
    private String descricaoConsulta;

    public static Consulta toConsulta(ConsultaDTO consultaDTO, byte[] dataSetFile) {
        var consulta = new Consulta();

        consulta.setDescricaoConsulta(consultaDTO.getDescricaoConsulta());
        consulta.setDataConsulta(LocalDateTime.now());
        consulta.setCsvArquivo(dataSetFile);

        return consulta;
    }
}
