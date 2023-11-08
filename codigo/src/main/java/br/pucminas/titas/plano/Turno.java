package br.pucminas.titas.plano;

import java.time.LocalTime;

public enum Turno {

    MANHA("Manhã", LocalTime.of(8, 0), LocalTime.of(12, 0)),
    TARDE("Tarde", LocalTime.of(12, 1), LocalTime.of(18, 0)),
    NOITE("Noite", LocalTime.of(18, 1), LocalTime.of(23, 59));

    private String nome;
    private LocalTime inicio;
    private LocalTime fim;

    Turno(String nome, LocalTime inicio, LocalTime fim) {
        this.nome = nome;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public boolean contem(LocalTime hora) {
        return hora.isAfter(inicio) && hora.isBefore(fim);
    }

}
