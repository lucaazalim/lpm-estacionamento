package br.pucminas.titas.excecoes;

public class VeiculoJaSaiuException extends Exception {

    public VeiculoJaSaiuException() {
        super("Este uso de vaga já foi concluído porque o veículo já saiu.");
    }

}
