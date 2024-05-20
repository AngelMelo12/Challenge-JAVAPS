package com.fiap.challenge.targetcustomer.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "T_TC_LOGIN")
@Data
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_login")
    private Long id;

    @Column(name = "nr_cnpj")
    private Long cnpj;

    @Column(name = "varchar_senha")
    private String senha;

    @OneToOne
    @JoinColumn(name = "id_cadastro")
    private Cadastro cadastro;

}
