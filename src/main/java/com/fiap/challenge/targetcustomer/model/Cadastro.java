package com.fiap.challenge.targetcustomer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "T_TC_CADSASTRO")
@Data
@ToString
public class Cadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cadastro")
    private Long id;

    @Column(name = "nr_cnpj")
    private Long cnpj;

    @Column(name = "varchar_senha")
    @NotBlank
    @Size(min = 5, max = 60, message = "Senha deve ter no mínimo 5 caracteres e no máximo 60 caracteres")
    private String senha;

    @Column(name = "nm_razaosocial")
    @Size(max = 80)
    private String razaoSocial;

    @OneToMany(mappedBy = "cadastro", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<EmailEmpresa> emailEmpresas;

    @OneToMany(mappedBy = "cadastro", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<EnderecoEmpresa> enderecosEmpresas;

    @OneToMany(mappedBy = "cadastro", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<TelefoneEmpresa> telefonesEmpresas;

}
