package br.pucminas.titas.excecoes;

public class VeiculoNaoEncontradoException extends Exception {

    public VeiculoNaoEncontradoException(String placa) {
        super("Veículo com placa " + placa + " não encontrado.");
    }

}
