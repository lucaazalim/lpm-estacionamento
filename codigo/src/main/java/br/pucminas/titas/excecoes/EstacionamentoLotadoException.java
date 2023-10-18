package br.pucminas.titas.excecoes;

public class EstacionamentoLotadoException extends Exception {

    public EstacionamentoLotadoException() {
        super("Este estacionamento não possui vagas disponíveis.");
    }

}
