package com.fiap.challenge.targetcustomer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "T_TC_ANALISE_DA_CONSULTA")
@Data
@ToString
public class AnaliseConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_analise_da_consulta")
    private Long id;

    @Column(name = "pdf_analise_da_consulta")
    @Lob
    private byte[] pdf;

    @Column(name = "st_analise_da_consulta")
    private char status;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    @JsonIgnore
    private Consulta consulta;

}
