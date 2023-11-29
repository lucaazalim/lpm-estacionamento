package br.pucminas.titas.entidades;

import br.pucminas.titas.enums.TipoPlano;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Stream;

/**
 * Representa um cliente de um estacionamento específico.
 */
public class Cliente implements Serializable {

    private final int id;
    private final String nome;
    private Plano plano;
    private final Map<String, Veiculo> veiculos;

    /**
     * Constrói um novo objeto Cliente com o nome e id fornecidos.
     * Inicializa um array vazio de veículos com um tamanho de 100.
     *
     * @param nome O nome do cliente.
     */
    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
        this.plano = TipoPlano.HORISTA.getPlano();
        this.veiculos = new HashMap<>();
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public Plano getPlano() {
        return this.plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    /**
     * Adiciona um veículo à coleção do cliente.
     *
     * @param veiculo O veículo a ser adicionado.
     */
    public void cadastrarVeiculo(Veiculo veiculo) {
        this.veiculos.putIfAbsent(veiculo.getPlaca().toUpperCase(), veiculo);
    }

    /**
     * Procura um veículo na lista de veículos do cliente.
     *
     * @param placa A placa a ser procurada.
     * @return O veículo com a placa correspondente, ou null se não encontrado.
     */
    public Veiculo procurarVeiculo(String placa) {
        Objects.requireNonNull(placa);
        return this.veiculos.get(placa.toUpperCase());
    }

    /**
     * Calcula o total de usos de todos os veículos do cliente.
     *
     * @return O total de usos de todos os veículos.
     */
    public int totalDeUsos() {
        return this.veiculos.values().stream()
                .mapToInt(Veiculo::totalDeUsos)
                .sum();
    }

    /**
     * Calcula o total de usos de todos os veículos do cliente.
     *
     * @param anoMes O ano e mês a serem consultados.
     * @return O total de usos de todos os veículos.
     */
    public int totalDeUsos(YearMonth anoMes) {
        return this.veiculos.values().stream()
                .mapToInt(veiculo -> veiculo.totalDeUsos(anoMes))
                .sum();
    }

    /**
     * Calcula o montante total arrecadado por todos os veículos do cliente.
     *
     * @return O montante total arrecadado por todos os veículos.
     */
    public double arrecadacaoTotal() {
        return this.veiculos.values().stream()
                .mapToDouble(Veiculo::totalArrecadado)
                .sum();
    }

    /**
     * Calcula o montante arrecadado por todos os veículos do cliente em um determinado mês.
     *
     * @param anoMes Ano e mês a serem consultados.
     * @return O montante total arrecadado no mês especificado.
     */
    public double arrecadacaoNoMes(YearMonth anoMes) {
        return this.veiculos.values().stream()
                .mapToDouble(veiculo -> veiculo.arrecadadoNoMes(anoMes))
                .sum();
    }

    /**
     * Recupera o histórico de uso de vaga do cliente em todos os seus veículos.
     *
     * @param de  data inicial de entrada
     * @param ate data final de entrada
     * @return O histórico de uso de vaga do cliente em todos os seus veículos.
     */
    public List<UsoDeVaga> historico(LocalDate de, LocalDate ate, Comparator<UsoDeVaga> comparador) {

        Objects.requireNonNull(de);
        Objects.requireNonNull(ate);

        Stream<UsoDeVaga> stream = this.veiculos.values().stream()
                .map(veiculo -> veiculo.historico(de, ate, null))
                .flatMap(List::stream);

        if (comparador != null) {
            stream = stream.sorted(comparador);
        }

        return stream.toList();

    }

    @Override
    public String toString() {
        return this.nome + " (ID: " + this.id + ") (Plano: " + this.plano + ")";
    }

}
