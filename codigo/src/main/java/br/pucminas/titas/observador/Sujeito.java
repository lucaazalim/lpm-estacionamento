package br.pucminas.titas.observador;

public interface Sujeito {

    void observar(Observador observador);

    void notificarTodos();

}
