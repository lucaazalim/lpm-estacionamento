package br.pucminas.titas.excecoes;

public class ServicoNaoTerminadoException extends Exception {

    public ServicoNaoTerminadoException() {
        super("Os serviços que estão sendo realizados neste veículo ainda não foram concluídos.");
    }

}
