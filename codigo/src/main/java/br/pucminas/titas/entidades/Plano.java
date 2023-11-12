package br.pucminas.titas.entidades;

import br.pucminas.titas.enums.Turno;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Plano implements Serializable {

    private String nome;

    public Plano(String nome) {
        this.nome = nome;
    }

    public abstract double calcularValor(LocalDateTime entrada, LocalDateTime saida);

    @Override
    public String toString() {
        return this.nome;
    }


    public static class Horista extends Plano {

        public Horista(String nome) {
            super(nome);
        }

        @Override
        public double calcularValor(LocalDateTime entrada, LocalDateTime saida) {

            Duration duration = Duration.between(entrada, saida);

            double minutos = duration.toMinutes();
            double fracaoMinutos = Math.floor(minutos / 15);

            double valorFracao = 4;
            double valorMaximo = 50;

            return Math.min(fracaoMinutos * valorFracao, valorMaximo);

        }

    }

    public static class Turnista extends Horista {

        private final Turno turno;

        public Turnista(Turno turno) {
            super("Turnista " + turno);
            this.turno = turno;
        }

        @Override
        public double calcularValor(LocalDateTime entrada, LocalDateTime saida) {

            if (this.turno.contem(entrada.toLocalTime())) {
                return 0;
            }

            return super.calcularValor(entrada, saida);

        }
    }

    public static class Mensalista extends Plano {

        public Mensalista() {
            super("Mensalista");
        }

        @Override
        public double calcularValor(LocalDateTime entrada, LocalDateTime saida) {
            return 0;
        }

    }

}
