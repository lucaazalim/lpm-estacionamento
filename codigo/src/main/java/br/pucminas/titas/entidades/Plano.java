package br.pucminas.titas.entidades;

import br.pucminas.titas.enums.Turno;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public interface Plano extends Serializable {

    double valorPago(LocalDateTime entrada, LocalDateTime saida);

    class Horista implements Plano {

        double VALOR_FRACAO = 4.0;
        double VALOR_MAXIMO = 50.0;

        @Override
        public double valorPago(LocalDateTime entrada, LocalDateTime saida) {

            Duration duration = Duration.between(entrada, saida);

            double minutos = duration.toMinutes();
            double fracaoMinutos = Math.floor(minutos / 15);

            return Math.min(fracaoMinutos * VALOR_FRACAO, VALOR_MAXIMO);

        }

        @Override
        public String toString() {
            return "Horista";
        }

    }

    class Turnista extends Horista {

        private Turno turno;

        public Turnista(Turno turno) {
            this.turno = turno;
        }

        @Override
        public double valorPago(LocalDateTime entrada, LocalDateTime saida) {

            if (this.turno.contem(entrada.toLocalTime())) {
                return 0;
            }

            return super.valorPago(entrada, saida);

        }

        @Override
        public String toString() {
            return "Turnista (" + this.turno + ")";
        }

    }

    class Mensalista implements Plano {

        @Override
        public double valorPago(LocalDateTime entrada, LocalDateTime saida) {
            return 0;
        }

        @Override
        public String toString() {
            return "Mensalista";
        }

    }

}
