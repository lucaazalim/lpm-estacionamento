package br.pucminas.titas.excecoes;

/**
 * Exceção lançada quando o serviço não foi terminado.
 */
public class ServicoNaoTerminadoException extends Exception {

    public ServicoNaoTerminadoException() {
        super("Os serviços que estão sendo realizados neste veículo ainda não foram concluídos.");
    }

}
