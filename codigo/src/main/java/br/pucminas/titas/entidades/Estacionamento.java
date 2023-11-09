package br.pucminas.titas.entidades;

import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.excecoes.*;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.*;

public class Estacionamento implements Serializable {

    private final String nome;
    private Map<Integer, Cliente> clientes;
    private List<Vaga> vagas;

    public Estacionamento(String nome, int fileiras, int vagasPorFileira) {

        this.nome = nome;
        this.clientes = new LinkedHashMap<>();
        this.vagas = new ArrayList<>();

        for (int i = 1; i <= fileiras; i++) {
            for (int j = 1; j <= vagasPorFileira; j++) {
                this.vagas.add(new Vaga(i, j));
            }
        }

    }

    /**
     * Adiciona um cliente ao estacionamento.
     *
     * @param cliente O cliente a ser adicionado.
     */
    public void addCliente(Cliente cliente) {
        Objects.requireNonNull(cliente);
        this.clientes.put(cliente.getId(), cliente);
    }

    /**
     * Estaciona um veículo em alguma vaga disponível.
     *
     * @param veiculo O veículo a ser estacionado.
     * @param servico O serviço a ser realizado ou null, caso nenhum serviço vá ser realizado.
     * @throws VeiculoNaoEncontradoException A placa informada não pertence a nenhum veículo cadastrado.
     * @throws EstacionamentoLotadoExcecao Não há vagas disponíveis neste estacionamento.
     * @throws VagaNaoDisponivelException A vaga escolhida não está disponível.
     */
    public void estacionar(Veiculo veiculo, Servico servico) throws EstacionamentoLotadoExcecao, VagaNaoDisponivelException {

        Vaga vaga = vagas.stream()
                .filter(Vaga::disponivel)
                .findFirst().orElseThrow(() -> new EstacionamentoLotadoExcecao(this));

        veiculo.estacionar(vaga, servico);

    }

    /**
     * Verifica se algum cliente possui veículo com a placa especificada.
     *
     * @param placa A placa do veículo a ser procurado.
     * @return o veículo correspondente.
     * @throws VeiculoNaoEncontradoException caso não exista veículos com a placa informada.
     */
    private Veiculo procurarVeiculo(String placa) throws VeiculoNaoEncontradoException {

        Veiculo veiculo;

        for (Cliente cliente : clientes.values()) {

            veiculo = cliente.procurarVeiculo(placa);

            if (veiculo != null) {
                return veiculo;
            }

        }

        throw new VeiculoNaoEncontradoException(placa);

    }

    /**
     * Libera a vaga do veículo com a placa especificada.
     *
     * @param placa A placa do veículo a ser procurado.
     * @return o valor a ser pago por este uso do estacionamento.
     * @throws VeiculoNaoEncontradoException caso não exista veículos com a placa informada.
     * @throws ServicoNaoTerminadoException caso o serviço ainda não tenha sido concluído.
     * @throws VeiculoNaoEstaEstacionadoException caso o veículo já não esteja estacionado.
     */
    public double sair(String placa) throws VeiculoNaoEncontradoException, ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException, VeiculoJaSaiuException {
        return this.procurarVeiculo(placa).sair();
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
