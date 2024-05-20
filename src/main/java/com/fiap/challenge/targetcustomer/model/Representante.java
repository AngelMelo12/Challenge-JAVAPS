package com.fiap.challenge.targetcustomer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_TC_REPRESENTANTE")
@Getter
@Setter
@NoArgsConstructor
public class Representante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_representante")
    private Long id;

    @Column(name = "nm_representante")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    @JsonIgnore
    private Consulta consulta;
}
