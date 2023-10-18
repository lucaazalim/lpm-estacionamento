package br.pucminas.titas.excecoes;

public class SairDeVagaDisponivelException extends Exception {
    private String message;

    public SairDeVagaDisponivelException(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
