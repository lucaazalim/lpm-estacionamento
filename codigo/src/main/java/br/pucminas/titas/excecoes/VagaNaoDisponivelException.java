package br.pucminas.titas.excecoes;

public class VagaNaoDisponivelException extends Exception {
    private String message;

    public VagaNaoDisponivelException(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}

