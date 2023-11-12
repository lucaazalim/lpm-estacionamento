package br.pucminas.titas.entidades;

import java.io.Serializable;

public class Vaga implements Serializable {

	private final String id;
	private boolean disponivel;

	public Vaga(int coluna, int linha) {
		this.id = ((char) ('A' + coluna)) + String.format("%02d", linha + 1);
		this.disponivel = true;
	}

	public boolean disponivel() {
		return this.disponivel;
	}

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
		return this.id + " " + (this.disponivel() ? "(Disponível)" : "(Ocupada)");
	}

}
