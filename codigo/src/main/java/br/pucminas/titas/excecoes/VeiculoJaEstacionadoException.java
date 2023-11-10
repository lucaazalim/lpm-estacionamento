package br.pucminas.titas.excecoes;

import br.pucminas.titas.entidades.Veiculo;

public class VeiculoJaEstacionadoException extends Exception {

    public VeiculoJaEstacionadoException(Veiculo veiculo) {
        super("O veículo '" + veiculo.getPlaca() + "' já está estacionado.");
    }

}
