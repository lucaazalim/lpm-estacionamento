package br.pucminas.titas;

public class Vaga {

	private String id;
	private boolean disponivel;

	public Vaga(String fila, int numero) {
		this.id = fila + String.format("%02d", String.valueOf(numero));
		this.disponivel = true;
	}

	public boolean estacionar() {
		if (disponivel) {
			disponivel = false;
			return true;
		}
		return false;
	}

	public boolean sair() {
		if (!disponivel) {
			disponivel = true;
			return true;
		}
		return false;
	}

	public boolean disponivel() {
		return disponivel;
	}

}
