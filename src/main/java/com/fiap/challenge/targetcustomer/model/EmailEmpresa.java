package com.fiap.challenge.targetcustomer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_TC_EMAIL_CONTATO")
@Getter
@Setter
@NoArgsConstructor
public class EmailEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_email_contato")
    private Long id;

    @Column(name = "varchar_email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_cadastro")
    @JsonIgnore
    private Cadastro cadastro;

    @Override
    public String toString() {
        return "EmailEmpresa{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", cadastro=" + cadastro.getCnpj() +
                '}';
    }
}
