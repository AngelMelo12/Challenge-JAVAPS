package com.fiap.challenge.targetcustomer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_TC_ENDERECO_EMPRESA")
@Getter
@Setter
@NoArgsConstructor
public class EnderecoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_endereco")
    private Long id;

    @Column(name = "nr_logradouro")
    private Long logradouro;

    private Long cep;

    @Column(name = "ds_ponto_de_referencia")
    private String descricaoPontoDeReferencia;

    @ManyToOne
    @JoinColumn(name = "id_cadastro")
    @JsonIgnore
    private Cadastro cadastro;

    @Override
    public String toString() {
        return "EnderecoEmpresa{" +
                "id=" + id +
                ", logradouro=" + logradouro +
                ", cep=" + cep +
                ", descricaoPontoDeReferencia='" + descricaoPontoDeReferencia + '\'' +
                ", cadastro=" + cadastro.getCnpj() +
                '}';
    }
}
