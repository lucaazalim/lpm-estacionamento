package br.pucminas.titas.plano;

import java.time.LocalTime;

public enum Turno {

    MANHA(LocalTime.of(8, 0), LocalTime.of(12, 0)),
    TARDE(LocalTime.of(12, 1), LocalTime.of(18, 0)),
    NOITE(LocalTime.of(18, 1), LocalTime.of(23, 59));

    private LocalTime inicio;
    private LocalTime fim;

    Turno(LocalTime inicio, LocalTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public boolean contem(LocalTime hora) {
        return hora.isAfter(inicio) && hora.isBefore(fim);
    }

}
