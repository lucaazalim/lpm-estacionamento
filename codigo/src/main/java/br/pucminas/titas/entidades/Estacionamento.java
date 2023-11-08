package br.pucminas.titas.entidades;

import br.pucminas.titas.excecoes.*;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Adiciona um veículo para um cliente especificado no estacionamento.
     * Recebe como parâmetro um veículo válido e um id de cliente existente. Em caso de cliente inexistente, 
     * lança uma exceção.
     * @param veiculo O veículo a ser adicionado.
     * @throws NoSuchElementException Em caso de cliente não existente.
     */
    public void addVeiculo(Veiculo veiculo) throws NoSuchElementException {
        veiculo.getCliente().addVeiculo(veiculo);
    }

    /**
     * Localiza um cliente no estacionamento usando o ID do cliente fornecido.
     *
     * @param idCliente O ID do cliente a ser localizado.
     * @return O objeto cliente, se encontrado, caso contrário, retorna null.
     */
    public Optional<Cliente> encontrarCliente(int idCliente) {
        return Optional.ofNullable(clientes.get(idCliente));
    }

    /**
     * Adiciona um cliente ao estacionamento.
     *
     * @param cliente O cliente a ser adicionado.
     */
    public void addCliente(Cliente cliente) {
        if (cliente != null) {
            this.clientes.put((Integer) cliente.getId(), cliente);
        }
    }

    //Parte responsável pelo aluno Gabriel.

    /**
    * Procura por vagas disponíveis. Estaciona o veículo.
    *
    * @param placa O veículo a ser estacionado.
    * @throws VeiculoNaoEncontradoException Em caso de não exista um carro com a placa passada.
    */
    public void estacionar(String placa) {

        Vaga vaga = this.encontrarVagaDisponivel().orElseThrow(() -> new VeiculoNaoEncontradoException(placa));
        Veiculo veiculo = this.procurarVeiculo(placa);

        try {
            veiculo.estacionar(vaga);
        } catch (VagaNaoDisponivelException ignored) {
            // Exceção pode ser ignorada porque já foi confirmado que a vaga está disponível
        }
    }

    /**
    * Procura por vagas disponíveis.
    *
    * @return a vaga encontrada. Se nenhuma estiver disponível, retorna null.
    */
    private Optional<Vaga> encontrarVagaDisponivel() {
        return vagas.stream()
                .filter(vaga -> vaga.disponivel())
                .findFirst();
    }

    /**
    * Verifica se o cliente possui veículo com a placa especificada.
    *
    * @param placa A placa do veículo a ser procurado.
    * @return o veículo correspondente.
    * @throws VeiculoNaoEncontradoException caso não exista veículos com essa placa
    */
    private Veiculo procurarVeiculo(String placa) {

        Veiculo veiculo;

        for (Cliente cliente : clientes.values()) {

            veiculo = cliente.possuiVeiculo(placa);

            if (veiculo != null) {
                return veiculo;
            }

        }

        throw new VeiculoNaoEncontradoException(placa);

    }

    /** 
    *Remove o veículo da vaga.
    *
    * @param placa A placa correspondente ao veículo.
    * @throws ServicoNaoTerminadoException
    * @throws VeiculoNaoEstaEstacionadoException
    */
    public double sair(String placa) throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {
        Veiculo veiculo = this.procurarVeiculo(placa);
        return veiculo.sair();
    }

    /**
    * Calcula o montante total arrecadado do estacionamento.
    *
    * @return total arrecadado do estacionamento.
    */
    public double totalArrecadado() {
        return clientes.values()
                .stream()
                .mapToDouble(cliente -> cliente.arrecadadoTotal())
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
    *  @return media, o valor médio por uso
    */
    public double valorMedioPorUso() {
        return clientes.values()
                .stream()
                .mapToDouble(Cliente::arrecadadoTotal)
                .average().orElse(0);
    }

    /**
     * Método para buscar os 5 melhores clientes de um mês
     *
     * @param anoMes Ano e mês a serem consultados.
     * @return retorna uma string com todos os top 5 do mês
     */
    public String top5Clientes(YearMonth anoMes) {
        return clientes.values()
                .stream()
                .sorted((cliente1, cliente2) -> Double.compare(cliente2.arrecadadoNoMes(anoMes), cliente1.arrecadadoNoMes(anoMes)))
                .limit(5)
                .map(Cliente::toString)
                .collect(Collectors.joining("; "));

    }

    public Map<Integer, Cliente> getClientes() {
        return this.clientes;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
