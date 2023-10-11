package br.pucminas.titas;

public class Cliente {

	private String nome;
	private String id;
	private Veiculo[] veiculos;
	private int contadorVeiculos = 0;

	public Cliente(String nome, String id) {
		this.nome = nome;
		this.id = id;
		this.veiculos = new Veiculo[100]; //limite de 100 veículos
	}

	public void addVeiculo(Veiculo veiculo) {
		if (contadorVeiculos < veiculos.length) {
			veiculos[contadorVeiculos] = veiculo;
			contadorVeiculos++;
		}
	}

	public Veiculo possuiVeiculo(String placa) {
		for (Veiculo veiculo : veiculos) {
			if (veiculo != null && veiculo.getPlaca().equals(placa)) { //classe veiculo implementar getPlaca
				return veiculo;
			}
		}
		return null;
	}

	public int totalDeUsos() {
		int total = 0;
		for (Veiculo veiculo : veiculos) {
			if (veiculo != null) {
				total += veiculo.getUsoTotal(); //classe veiculo implementar getUsoTotal
			}
		}
		return total;
	}

	public double arrecadadoPorVeiculo(String placa) {
		Veiculo veiculo = possuiVeiculo(placa);
		if (veiculo != null) {
			return veiculo.getArrecadacao(); //classe veiculo implementar getArrecadacao
		}
		return 0;
	}

	public double arrecadadoTotal() {
		double total = 0;
		for (Veiculo veiculo : veiculos) {
			if (veiculo != null) {
				total += veiculo.getArrecadacao();
			}
		}
		return total;
	}

	public double arrecadadoNoMes(int mes) {
		double total = 0;
		for (Veiculo veiculo : veiculos) {
			if (veiculo != null) {
				total += veiculo.getArrecadacaoNoMes(mes); //classe veiculo implementar getArrecadacaoNoMes
			}
		}
		return total;
	}


}
