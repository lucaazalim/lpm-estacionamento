package br.pucminas.titas.excecoes;

import br.pucminas.titas.entidades.Vaga;

public class VagaNaoDisponivelException extends Exception {

    public VagaNaoDisponivelException(Vaga vaga) {
        super("A vaga '" + vaga + "' não está disponível.");
    }

}

