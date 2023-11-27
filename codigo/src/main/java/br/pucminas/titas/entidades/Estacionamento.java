package br.pucminas.titas.entidades;

import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.enums.TipoPlano;
import br.pucminas.titas.excecoes.*;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.*;

/**
 * Representa um estacionamento gerenciado pelo sistema.
 */
public class Estacionamento implements Serializable {

    private final String nome;
    private final int colunas, linhas;
    private Map<Integer, Cliente> clientes;
    private List<Vaga> vagas;

    public Estacionamento(String nome, int colunas, int linhas) {

        this.nome = nome;
        this.colunas = colunas;
        this.linhas = linhas;
        this.clientes = new LinkedHashMap<>();
        this.vagas = new ArrayList<>();

        for (int coluna = 0; coluna < colunas; coluna++) {
            for (int linha = 0; linha < linhas; linha++) {
                this.vagas.add(new Vaga(coluna, linha));
            }
        }

    }

    public String getNome() {
        return this.nome;
    }

    public int getColunas() {
        return this.colunas;
    }

    public int getLinhas() {
        return this.linhas;
    }

    /**
     * @return uma versão imutável do mapa de clientes.
     */
    public Map<Integer, Cliente> getClientes() {
        return Collections.unmodifiableMap(this.clientes);
    }

    /**
     * @return uma versão imutável da lista de vagas.
     */
    public List<Vaga> getVagas() {
        return Collections.unmodifiableList(this.vagas);
    }

    public Vaga procurarVaga(int linha, int coluna) {
        return this.vagas.stream()
                .filter(vaga -> vaga.getLinha() == linha && vaga.getColuna() == coluna)
                .findFirst().orElse(null);
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
        return this.clientes.values()
                .stream()
                .mapToDouble(Cliente::arrecadacaoTotal)
                .sum();
    }

    /**
     * Calcula o montante total arrecadado do estacionamento em determinado mês.
     *
     * @param anoMes Ano e mês a serem consultados.
     * @return o total arrecadado do estacionamento no mês.
     */
    public double totalArrecadadoNoMes(YearMonth anoMes) {
        return this.clientes.values()
                .stream()
                .mapToDouble(cliente -> cliente.arrecadacaoNoMes(anoMes))
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
                .mapToDouble(Cliente::arrecadacaoTotal)
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
        return this.clientes.values()
                .stream()
                .sorted((cliente1, cliente2) -> Double.compare(cliente2.arrecadacaoNoMes(anoMes), cliente1.arrecadacaoNoMes(anoMes)))
                .limit(limite)
                .toList();

    }

    /**
     * Retorna a média de usos do estacionamento por cliente
     * em um mês específico de clientes de um plano específico.
     *
     * @param anoMes ano e mês a serem consultados.
     * @param plano plano a ser consultado.
     * @return o total de usos do estacionamento no mês informado de clientes do plano informado.
     */
    public double quantidadeMediaDeUsos(YearMonth anoMes, Plano plano) {
        return this.clientes.values()
                .stream()
                .filter(cliente -> cliente.getPlano().equals(plano))
                .mapToInt(cliente -> cliente.totalDeUsos(anoMes))
                .average().orElse(0);
    }

    /**
     * Retorna a média de arrecadação do estacionamento por cliente
     * em um mês específico de clientes de um plano específico.
     *
     * @param anoMes ano e mês a serem consultados.
     * @param plano tipo de plano a ser consultado.
     * @return o total de arrecadação do estacionamento no mês informado de clientes do plano informado.
     */
    public double arrecadacaoMediaPorCliente(YearMonth anoMes, Plano plano) {
        return this.clientes.values()
                .stream()
                .filter(cliente -> cliente.getPlano().equals(plano))
                .mapToDouble(cliente -> cliente.arrecadacaoNoMes(anoMes))
                .average().orElse(0);
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
