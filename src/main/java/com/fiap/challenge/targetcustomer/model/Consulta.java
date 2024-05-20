package com.fiap.challenge.targetcustomer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_TC_CONSULTA")
@Data
@ToString
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_consulta")
    private Long id;

    @Column(name = "ds_consulta")
    private String descricaoConsulta;

    @Column(name = "dt_consulta")
    private LocalDateTime dataConsulta;

    @Column(name = "blob_csv_arquivo")
    @Lob
    private byte[] csvArquivo;

    @ManyToOne
    @JoinColumn(name = "id_cadastro")
    @JsonIgnore
    private Cadastro cadastro;
}
