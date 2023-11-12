package br.pucminas.titas.excecoes;

/**
 * Exceção lançada quando ocorre um erro na aplicação.
 */
public class AppExcecao extends Exception {

    public AppExcecao(String mensagem) {
        super(mensagem);
    }

}
