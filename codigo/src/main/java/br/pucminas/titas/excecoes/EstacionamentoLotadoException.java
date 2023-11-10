package br.pucminas.titas.excecoes;

import br.pucminas.titas.entidades.Estacionamento;

public class EstacionamentoLotadoException extends Exception {

    public EstacionamentoLotadoException(Estacionamento estacionamento) {
        super("O estacionamento '" + estacionamento + "' está lotado.");
    }

}
