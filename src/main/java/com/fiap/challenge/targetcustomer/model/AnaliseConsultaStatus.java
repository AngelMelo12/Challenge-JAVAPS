package com.fiap.challenge.targetcustomer.model;

public enum AnaliseConsultaStatus {

    INDISPONIVEL('i'),
    AVALIADO('a');

    public final char value;

    AnaliseConsultaStatus(char value) {
        this.value = value;
    }
}
