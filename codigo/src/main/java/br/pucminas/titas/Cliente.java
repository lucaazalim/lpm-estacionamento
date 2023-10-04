package br.pucminas.titas;

public class Cliente {

	private String nome;
	private String id;
	private Veiculo[] veiculos;
	private int contadorVeiculos = 0;

	public Cliente(String nome, String id) {
		this.nome = nome;
		this.id = id;
	}

	public void addVeiculo(Veiculo veiculo) {
		if (contadorVeiculos < veiculos.length) {
			veiculos[contadorVeiculos] = veiculo;
			contadorVeiculos++;
		}else {
			System.out.println("O vetor veiculos está cheio.");
		}
	}

	public Veiculo possuiVeiculo(String placa) {
		
	}

	public int totalDeUsos() {
		
	}

	public double arrecadadoPorVeiculo(String placa) {
		
	}

	public double arrecadadoTotal() {
		
	}

	public double arrecadadoNoMes(int mes) {
		
	}

}
