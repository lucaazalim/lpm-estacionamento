package br.pucminas.titas;

public class Vaga {

	private String id;
	private boolean disponivel;

	public Vaga(int fila, int numero) {
		this.id = String.valueOf(fila) + String.format("%02d", String.valueOf(numero));
		this.disponivel = true;
	}

	public boolean estacionar() {
		disponivel = false;
	}

	public boolean sair() {
		disponivel = true;
	}

	public boolean disponivel() {
		return disponivel;
	}

}
