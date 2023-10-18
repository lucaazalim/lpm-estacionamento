package br.pucminas.titas;

public class ServicoNaoTerminadoException extends Exception {
    private String message;

    public ServicoNaoTerminadoException(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
