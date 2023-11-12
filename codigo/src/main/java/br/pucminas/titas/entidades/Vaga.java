package br.pucminas.titas.entidades;

import java.io.Serializable;

/**
 * Representa uma vaga do estacionamento.
 */
public class Vaga implements Serializable {

	private final String id;
	private boolean disponivel;

	/**
	 * Cria uma nova vaga com os dados informados.
	 *
	 * @param coluna Coluna da vaga
	 * @param linha Linha da vaga
	 */
	public Vaga(int coluna, int linha) {
		this.id = ((char) ('A' + coluna)) + String.format("%02d", linha + 1);
		this.disponivel = true;
	}

	/**
	 * Retorna se a vaga está disponível ou não.
	 *
	 * @return true se a vaga estiver disponível e false caso contrário.
	 */
	public boolean disponivel() {
		return this.disponivel;
	}

	/**
	 * Altera o estado de disponibilidade da vaga.
	 *
	 * @param disponivel true para disponível e false para indisponível.
	 */
	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
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

}
