package br.pucminas.titas.excecoes;

import br.pucminas.titas.entidades.Estacionamento;

public class EstacionamentoLotadoExcecao extends Exception {

    public EstacionamentoLotadoExcecao(Estacionamento estacionamento) {
        super("O estacionamento '" + estacionamento + "' está lotado.");
    }

}
