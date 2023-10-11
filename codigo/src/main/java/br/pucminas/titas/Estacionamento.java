package br.pucminas.titas;

import java.util.ArrayList;

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
        this.id = new Cliente[0];
        this.vagas = new Vaga[0];
        gerarVagas();
    }

    public void addVeiculo(Veiculo veiculo, String idCli) {
        Cliente cliente = encontrarCliente(idCli);
        if (cliente != null) {
            cliente.addVeiculo(veiculo);
        }
    }

    private Cliente encontrarCliente(String idCli) {
		return null;
	}

	public void addCliente(Cliente cliente) {
        Cliente[] novosClientes = new Cliente[id.length + 1];
        System.arraycopy(id, 0, novosClientes, 0, id.length);
        novosClientes[id.length] = cliente;
        id = novosClientes;
    }

    private void gerarVagas() {
        int totalVagas = quantFileiras * vagasPorFileira;
        vagas = new Vaga[totalVagas];
        for (int i = 0; i < totalVagas; i++) {
            vagas[i] = new Vaga("Y" + (i / vagasPorFileira) + (i % vagasPorFileira));
        }
    }
}

    

   

