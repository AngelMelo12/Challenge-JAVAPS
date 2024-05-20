package com.fiap.challenge.targetcustomer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_TC_TELEFONE_EMPRESA")
@Getter
@Setter
@NoArgsConstructor
public class TelefoneEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_telefone")
    private Long id;

    @Column(name = "nr_ddi")
    private Long ddi;

    @Column(name = "nr_ddd")
    private Long ddd;

    @Column(name = "nr_telefone")
    private Long telefone;

    @Size(max = 20)
    private String tipoTelefone;

    @ManyToOne
    @JoinColumn(name = "id_cadastro")
    @JsonIgnore
    private Cadastro cadastro;

    @Override
    public String toString() {
        return "TelefoneEmpresa{" +
                "id=" + id +
                ", ddi=" + ddi +
                ", ddd=" + ddd +
                ", telefone=" + telefone +
                ", tipoTelefone='" + tipoTelefone + '\'' +
                ", cadastro=" + cadastro.getCnpj() +
                '}';
    }
}
