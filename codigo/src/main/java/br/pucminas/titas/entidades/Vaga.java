package br.pucminas.titas.entidades;

import java.io.Serializable;

public class Vaga implements Serializable {

	private String id;
	private boolean disponivel;

	public Vaga(String fila, int numero) {
		this.id = fila + String.format("%02d", numero);
		this.disponivel = true;
	}

	/**
	 * Tenta utilizar a vaga
	 * @return true se foi possível | false se não
	 */
	public boolean estacionar() {
		if (disponivel) {
			disponivel = false;
			return true;
		}
		return false;
	}

	/**
	 * Tenta liberar a vaga
	 * @return true se foi possível | false se não
	 */
	public boolean sair() {
		if (!disponivel) {
			disponivel = true;
			return true;
		}
		return false;
	}

	/**
	 * Retorna se a vaga está disponível
	 * @return
	 */
	public boolean disponivel() {
		return disponivel;
	}

}
