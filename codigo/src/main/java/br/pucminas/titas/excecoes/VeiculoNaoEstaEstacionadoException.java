package br.pucminas.titas.excecoes;

/**
 * Exceção lançada quando o veículo não está estacionado.
 */
public class VeiculoNaoEstaEstacionadoException extends Exception {

    public VeiculoNaoEstaEstacionadoException() {
        super("O veículo já não está estacionado no momento.");
    }
    
}
