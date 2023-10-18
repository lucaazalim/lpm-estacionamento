package br.pucminas.titas.excecoes;

public class VeiculoNaoEstaEstacionadoException extends Exception {

    public VeiculoNaoEstaEstacionadoException() {
        super("O veículo já não está estacionado no momento.");
    }
    
}
