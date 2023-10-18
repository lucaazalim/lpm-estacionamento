package br.pucminas.titas.entidades;

import br.pucminas.titas.excecoes.*;

import java.io.Serializable;

public class Estacionamento implements Serializable {

    private final String nome;
    private Cliente[] clientes;
    private Vaga[] vagas;
    private final int quantFileiras;
    private final int vagasPorFileira;

    public Estacionamento(String nome, int fileiras, int vagasPorFila) {
        this.nome = nome;
        this.clientes = new Cliente[100];
        this.quantFileiras = fileiras;
        this.vagasPorFileira = vagasPorFila;
        gerarVagas();
    }

    /**
     * Adiciona um veículo para um cliente especificado no estacionamento.
     *
     * @param veiculo O veículo a ser adicionado.
     * @param idCliente   O ID do cliente proprietário do veículo.
     */
    public void addVeiculo(Veiculo veiculo, String idCliente) {
        Cliente cliente = this.encontrarCliente(idCliente);
        if (cliente != null) {
            cliente.addVeiculo(veiculo);
        }
    }

    /**
     * Localiza um cliente no estacionamento usando o ID do cliente fornecido.
     *
     * @param idCliente O ID do cliente a ser localizado.
     * @return O objeto cliente, se encontrado, caso contrário, retorna null.
     */
    private Cliente encontrarCliente(String idCliente) {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getId().equals(idCliente)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Adiciona um cliente ao estacionamento.
     *
     * @param cliente O cliente a ser adicionado.
     */
    public void addCliente(Cliente cliente) {
        for(int i = 0; i < this.clientes.length; i++) {
            if(this.clientes[i] == null) {
                this.clientes[i] = cliente;
                break;
            }
        }
    }

    /**
     * Gera vagas de estacionamento com base no número de fileiras e vagas por fileira.
     */
    private void gerarVagas() {
        int totalVagas = quantFileiras * vagasPorFileira;
        this.vagas = new Vaga[totalVagas];
        for (int i = 0; i < totalVagas; i++) {
            vagas[i] = new Vaga("Y", (i / vagasPorFileira) + (i % vagasPorFileira));
        }
    }

    //Parte responsável pelo aluno Gabriel.

    public void estacionar(String placa) throws EstacionamentoLotadoException {

        Vaga vagaDisponivel = this.encontrarVagaDisponivel();
        Veiculo veiculo = this.procurarVeiculo(placa);

        if (vagaDisponivel == null) {
            throw new EstacionamentoLotadoException();
        } else {
            try {
                veiculo.estacionar(vagaDisponivel);
            } catch (VagaNaoDisponivelException ignored) {
                // Exceção pode ser ignorada porque já foi confirmado que a vaga está disponível
            }
        }
    }

    private Vaga encontrarVagaDisponivel() {
        for (Vaga vaga : this.vagas) {
            if (vaga.disponivel()) {
                return vaga;
            }
        }
        return null;
    }

    private Veiculo procurarVeiculo(String placa) {

        Veiculo veiculo;

        for (Cliente cliente : clientes) {

            veiculo = cliente.possuiVeiculo(placa);

            if (veiculo != null) {
                return veiculo;
            }

        }

        throw new VeiculoNaoEncontradoException(placa);

    }

    public double sair(String placa) throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {

        Veiculo veiculo = this.procurarVeiculo(placa);
        return veiculo.sair();

    }

    public double totalArrecadado() {
        double total = 0;
        for (Cliente cliente : clientes) {
            total += cliente.arrecadadoTotal();
        }
        return total;
    }

    public double arrecadacaoNoMes(int mes) {
        double total = 0;
        for (Cliente cliente : clientes) {
            total += cliente.arrecadadoNoMes(mes);
        }
        return total;
    }

    public double valorMedioPorUso() {
        return 0; // TODO
    }

    public String top5Clientes(int mes) {
        return null; // TODO
    }
}
