package br.pucminas.titas.entidades;

import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.excecoes.*;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.*;

/**
 * Representa um estacionamento gerenciado pelo sistema.
 */
public class Estacionamento implements Serializable {

    private final String nome;
    private Map<Integer, Cliente> clientes;
    private List<Vaga> vagas;

    public Estacionamento(String nome, int colunas, int linhas) {

        this.nome = nome;
        this.clientes = new LinkedHashMap<>();
        this.vagas = new ArrayList<>();

        for (int coluna = 0; coluna < colunas; coluna++) {
            for (int linha = 0; linha < linhas; linha++) {
                this.vagas.add(new Vaga(coluna, linha));
            }
        }

    }

    public List<Vaga> getVagas() {
        return Collections.unmodifiableList(this.vagas);
    }

    /**
     * Cadastra um cliente no estacionamento.
     *
     * @param nome Nome do cliente a ser cadastrado.
     */
    public Cliente cadastrarCliente(String nome) {

        Objects.requireNonNull(nome);

        Cliente cliente = new Cliente(this.clientes.size() + 1, nome);

        this.clientes.put(cliente.getId(), cliente);

        return cliente;

    }

    /**
     * Estaciona um veículo em alguma vaga disponível.
     *
     * @param veiculo O veículo a ser estacionado.
     * @param servico O serviço a ser realizado ou null, caso nenhum serviço vá ser realizado.
     * @throws EstacionamentoLotadoException Caso o estacionamento não tenha vagas disponíveis.
     * @throws VeiculoJaEstacionadoException Caso o veículo informado já esteja estacionado.
     */
    public void estacionar(Veiculo veiculo, Servico servico) throws EstacionamentoLotadoException, VeiculoJaEstacionadoException {

        Vaga vaga = this.vagas.stream()
                .filter(Vaga::disponivel)
                .findFirst().orElseThrow(() -> new EstacionamentoLotadoException(this));

        veiculo.estacionar(vaga, servico);

    }

    /**
     * Verifica se algum cliente possui veículo com a placa especificada.
     *
     * @param placa A placa do veículo a ser procurado.
     * @return o veículo correspondente ou null caso não seja encontrado.
     */
    public Veiculo procurarVeiculo(String placa) {

        return this.clientes.values().stream()
                .map(cliente -> cliente.procurarVeiculo(placa))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);

    }

    /**
     * Libera a vaga do veículo com a placa especificada.
     *
     * @param veiculo O veículo a ser liberado.
     * @return o valor a ser pago por este uso do estacionamento.
     * @throws ServicoNaoTerminadoException       caso o serviço ainda não tenha sido concluído.
     * @throws VeiculoNaoEstaEstacionadoException caso o veículo já não esteja estacionado.
     */
    public double sair(Veiculo veiculo) throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {
        return veiculo.sair();
    }

    /**
     * Calcula o montante total arrecadado pelo estacionamento.
     *
     * @return total arrecadado pelo estacionamento.
     */
    public double totalArrecadado() {
        return clientes.values()
                .stream()
                .mapToDouble(Cliente::arrecadadoTotal)
                .sum();
    }

    /**
     * Calcula o montante total arrecadado do estacionamento em determinado mês.
     *
     * @param anoMes Ano e mês a serem consultados.
     * @return o total arrecadado do estacionamento no mês.
     */
    public double totalArrecadadoNoMes(YearMonth anoMes) {
        return clientes.values()
                .stream()
                .mapToDouble(cliente -> cliente.arrecadadoNoMes(anoMes))
                .sum();
    }

    /**
     * Calcula o valor médio de cada utilização do estacionamento.
     *
     * @return media, o valor médio por uso
     */
    public double valorMedioPorUso() {
        return clientes.values()
                .stream()
                .mapToDouble(Cliente::arrecadadoTotal)
                .average().orElse(0);
    }

    /**
     * Retorna os clientes que mais utilizaram o estacionamento.
     *
     * @param anoMes Ano e mês a serem consultados.
     * @param limite O limite de clientes a serem retornados.
     * @return uma lista com os clientes que mais utilizaram o estacionamento.
     */
    public List<Cliente> topClientes(YearMonth anoMes, int limite) {
        return clientes.values()
                .stream()
                .sorted((cliente1, cliente2) -> Double.compare(cliente2.arrecadadoNoMes(anoMes), cliente1.arrecadadoNoMes(anoMes)))
                .limit(limite)
                .toList();

    }

    /**
     * @return uma versão imutável do mapa de clientes.
     */
    public Map<Integer, Cliente> getClientes() {
        return Collections.unmodifiableMap(this.clientes);
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
