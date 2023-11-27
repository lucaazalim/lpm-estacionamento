package br.pucminas.titas.entidades;

import br.pucminas.titas.observador.Observador;
import br.pucminas.titas.observador.Sujeito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma vaga do estacionamento.
 */
public class Vaga implements Serializable, Sujeito {

	private final int coluna, linha;
	private final String id;
	private UsoDeVaga usoDeVaga;

	private List<Observador> observadores = new ArrayList<>();

	/**
	 * Cria uma nova vaga com os dados informados.
	 *
	 * @param coluna Coluna da vaga
	 * @param linha Linha da vaga
	 */
	public Vaga(int coluna, int linha) {
		this.coluna = coluna;
		this.linha = linha;
		this.id = ((char) ('A' + coluna)) + String.format("%02d", linha + 1);
	}

	public int getColuna() {
		return this.coluna;
	}

	public int getLinha() {
		return this.linha;
	}

	/**
	 * Retorna se a vaga está disponível ou não.
	 *
	 * @return true se a vaga estiver disponível e false caso contrário.
	 */
	public boolean disponivel() {
		return this.usoDeVaga == null;
	}

	public UsoDeVaga getUsoDeVaga() {
		return this.usoDeVaga;
	}

	public void estacionar(UsoDeVaga usoDeVaga) {
		this.usoDeVaga = usoDeVaga;
		this.notificarTodos();
	}

	@Override
	public boolean equals(Object obj) {

		if(obj instanceof Vaga vaga) {
			return vaga.id.equals(this.id);
		}

		return false;

	}

	@Override
	public String toString() {
		return this.id;
	}

	@Override
	public void observar(Observador observador) {
		this.observadores.add(observador);
	}

	@Override
	public void notificarTodos() {
		this.observadores.forEach(Observador::notificar);
	}

}
