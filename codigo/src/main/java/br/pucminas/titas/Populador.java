package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.UsoDeVaga;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.enums.TipoPlano;
import br.pucminas.titas.excecoes.EstacionamentoLotadoException;
import br.pucminas.titas.excecoes.VeiculoJaEstacionadoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Populador {

    private static final Random RANDOM = new Random();

    public static List<Estacionamento> gerarEstacionamentos() {

        List<Estacionamento> estacionamentos = new ArrayList<>();

        {
            Estacionamento estacionamento = new Estacionamento("Estapar", 10, 20);

            Cliente cliente1 = estacionamento.cadastrarCliente("João Silva");
            cliente1.setPlano(TipoPlano.MENSALISTA.get());

            Cliente cliente2 = estacionamento.cadastrarCliente("Ana Silva");
            cliente2.setPlano(TipoPlano.TURNISTA_MANHA.get());

            Veiculo veiculo1 = new Veiculo("ABC123", cliente1);
            Veiculo veiculo2 = new Veiculo("DEF456", cliente2);

            veiculo1.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(0),
                    veiculo1,
                    Servico.LAVAGEM,
                    LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                    LocalDateTime.of(2023, 1, 1, 12, 0, 0)
            ));

            veiculo1.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(1),
                    veiculo1,
                    Servico.POLIMENTO,
                    LocalDateTime.of(2023, 1, 3, 9, 0, 0),
                    LocalDateTime.of(2023, 1, 3, 17, 0, 0)
            ));

            veiculo2.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(2),
                    veiculo1,
                    null,
                    LocalDateTime.of(2023, 2, 1, 7, 0, 0),
                    LocalDateTime.of(2023, 2, 1, 18, 0, 0)
            ));

            veiculo2.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(3),
                    veiculo1,
                    Servico.MANOBRISTA,
                    LocalDateTime.of(2023, 3, 3, 17, 0, 0),
                    LocalDateTime.of(2023, 3, 3, 18, 0, 0)
            ));

            cliente1.cadastrarVeiculo(veiculo1);
            cliente2.cadastrarVeiculo(veiculo2);

            estacionamentos.add(estacionamento);
        }

        {
            Estacionamento estacionamento = new Estacionamento("ParkMe", 5, 15);

            Cliente cliente1 = estacionamento.cadastrarCliente("Maria Fernandes");
            cliente1.setPlano(TipoPlano.TURNISTA_NOITE.get());

            Cliente cliente2 = estacionamento.cadastrarCliente("João Oliveira");
            cliente2.setPlano(TipoPlano.HORISTA.get());

            Veiculo veiculo1 = new Veiculo("ABC456", cliente1);
            Veiculo veiculo2 = new Veiculo("DEF123", cliente2);

            veiculo1.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(0),
                    veiculo1,
                    Servico.POLIMENTO,
                    LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                    LocalDateTime.of(2023, 2, 1, 12, 0, 0)
            ));

            veiculo1.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(1),
                    veiculo1,
                    null,
                    LocalDateTime.of(2023, 1, 3, 6, 0, 0),
                    LocalDateTime.of(2023, 1, 3, 7, 0, 0)
            ));

            veiculo2.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(2),
                    veiculo1,
                    null,
                    LocalDateTime.of(2023, 2, 1, 7, 0, 0),
                    LocalDateTime.of(2023, 2, 1, 18, 0, 0)
            ));

            veiculo2.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(3),
                    veiculo1,
                    Servico.MANOBRISTA,
                    LocalDateTime.of(2023, 3, 3, 17, 0, 0),
                    LocalDateTime.of(2023, 3, 3, 18, 0, 0)
            ));

            cliente1.cadastrarVeiculo(veiculo1);
            cliente2.cadastrarVeiculo(veiculo2);

            estacionamentos.add(estacionamento);
        }

        {
            Estacionamento estacionamento = new Estacionamento("AutoPark", 3, 5);

            Cliente cliente1 = estacionamento.cadastrarCliente("Tiago Martins");
            cliente1.setPlano(TipoPlano.TURNISTA_TARDE.get());

            Cliente cliente2 = estacionamento.cadastrarCliente("André Almeida");
            cliente2.setPlano(TipoPlano.MENSALISTA.get());

            Veiculo veiculo1 = new Veiculo("GHI123", cliente1);
            Veiculo veiculo2 = new Veiculo("JKL456", cliente2);

            veiculo1.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(0),
                    veiculo1,
                    Servico.MANOBRISTA,
                    LocalDateTime.of(2023, 5, 1, 9, 0, 0),
                    LocalDateTime.of(2023, 5, 1, 10, 0, 0)
            ));

            veiculo1.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(1),
                    veiculo1,
                    null,
                    LocalDateTime.of(2023, 3, 27, 1, 0, 0),
                    LocalDateTime.of(2023, 3, 27, 3, 0, 0)
            ));

            veiculo2.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(2),
                    veiculo1,
                    Servico.LAVAGEM,
                    LocalDateTime.of(2023, 3, 10, 5, 0, 0),
                    LocalDateTime.of(2023, 3, 10, 7, 0, 0)
            ));

            veiculo2.cadastrarUsoDeVaga(new UsoDeVaga(
                    estacionamento.getVagas().get(3),
                    veiculo1,
                    Servico.MANOBRISTA,
                    LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                    LocalDateTime.of(2023, 1, 1, 14, 0, 0)
            ));

            cliente1.cadastrarVeiculo(veiculo1);
            cliente2.cadastrarVeiculo(veiculo2);

            estacionamentos.add(estacionamento);
        }

        return estacionamentos;

    }

}
