package br.pucminas.titas.plano;

import java.time.Duration;
import java.time.LocalDateTime;

public interface Plano {

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

    }

    class Turnista extends Horista {

        private Turno turno;

        @Override
        public double valorPago(LocalDateTime entrada, LocalDateTime saida) {

            if (turno.contem(entrada.toLocalTime())) {
                return 0;
            }

            return super.valorPago(entrada, saida);

        }

    }

    class Mensalista implements Plano {

        @Override
        public double valorPago(LocalDateTime entrada, LocalDateTime saida) {
            return 0;
        }

    }

}
