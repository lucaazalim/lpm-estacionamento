package br.pucminas.titas;

public class Vaga {

	private String id;
	private boolean disponivel;

	public Vaga(int fila, int numero) {
		this.id = String.valueOf(fila) + String.format("%02d", String.valueOf(numero));
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
