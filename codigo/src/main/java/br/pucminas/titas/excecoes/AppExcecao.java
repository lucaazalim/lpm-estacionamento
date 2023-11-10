package br.pucminas.titas.excecoes;

public class AppExcecao extends Exception {

    public AppExcecao(String mensagem) {
        super(mensagem);
    }

    public AppExcecao(Exception exception) {
        super(exception);
    }

}
