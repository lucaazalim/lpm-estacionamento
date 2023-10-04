package br.pucminas.titas;

public class Estacionamento {

	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome = nome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
	}

	public void addVeiculo(Veiculo veiculo, String idCli) {
		
	}

	public void addCliente(Cliente cliente) {
		
	}

	private void gerarVagas() {
		
	}

	public void estacionar(String placa) {
		for(int i = 0; i<Vaga.lenght(); i++) {
			if(Vaga[i] == "") {
				Vaga[i] = placa;
			}
		}
	}

	public double sair(String placa) {
		for(int i = 0; Vaga.lenght(); i++) {
			iv(Vaga[i] == placa) {
				Vaga[i] = "";
			}
		}
	}

	public double totalArrecadado() {
		
	}

	public double arrecadacaoNoMes(int mes) {
		
	}

	public double valorMedioPorUso() {
		
	}

	public String top5Clientes(int mes) {
		
	}

}
