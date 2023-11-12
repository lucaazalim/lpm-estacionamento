package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.UsoDeVaga;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.enums.TipoPlano;
import br.pucminas.titas.excecoes.EstacionamentoLotadoException;
import br.pucminas.titas.excecoes.VeiculoJaEstacionadoException;

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

            cliente1.cadastrarVeiculo(veiculo1);
            cliente2.cadastrarVeiculo(veiculo2);

            estacionamentos.add(estacionamento);
        }

        return estacionamentos;

    }

}
