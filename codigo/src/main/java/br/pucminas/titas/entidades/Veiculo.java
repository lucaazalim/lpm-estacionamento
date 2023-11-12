package br.pucminas.titas.entidades;

import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.excecoes.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;

public class Veiculo implements Serializable {

    private final String placa;
    private final Cliente cliente;
    private final LinkedList<UsoDeVaga> usosDeVaga;

    /**
     * Cria um novo veículo com a placa informada.
     *
     * @param placa Placa do veículo
     */
    public Veiculo(String placa, Cliente cliente) {
        this.placa = placa;
        this.cliente = cliente;
        this.usosDeVaga = new LinkedList<>();
    }

    public String getPlaca() {
        return this.placa;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Estaciona o veículo na vaga informada.
     *
     * @param vaga    Vaga onde o veículo será estacionado
     * @param servico Serviço a ser realizado ou null, caso nenhum serviço vá ser realizado.
     * @throws VeiculoJaEstacionadoException caso o veículo já esteja estacionado.
     */
    public void estacionar(Vaga vaga, Servico servico) throws VeiculoJaEstacionadoException {

        if (!this.usosDeVaga.isEmpty() && !this.usosDeVaga.getLast().saiu()) {
            throw new VeiculoJaEstacionadoException(this);
        }

        UsoDeVaga usoDeVaga = new UsoDeVaga(vaga, this, servico);
        this.usosDeVaga.add(usoDeVaga);

        vaga.setDisponivel(false);

    }

    /**
     * Dispara {@see UsoDeVaga#sair()} no último uso de vaga do veículo.
     *
     * @return valor pago pelo veículo.
     * @throws ServicoNaoTerminadoException
     * @throws VeiculoNaoEstaEstacionadoException
     */
    public double sair() throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {

        UsoDeVaga usoDeVaga;

        try {
            usoDeVaga = this.usosDeVaga.getLast();
        } catch (NoSuchElementException e) {
            usoDeVaga = null;
        }

        if (usoDeVaga != null) {
            return usoDeVaga.sair();
        }

        throw new VeiculoNaoEstaEstacionadoException();

    }

    /**
     * Retorna o total arrecadado com o veículo.
     *
     * @return total arrecadado com o veículo.
     */
    public double totalArrecadado() {

        return this.usosDeVaga.stream().mapToDouble(UsoDeVaga::valorPago).sum();

    }

    /**
     * Retorna o total arrecadado com o veículo no mês informado.
     *
     * @param anoMes Ano e mês a serem consultados.
     * @return total arrecadado com o veículo no mês informado.
     */
    public double arrecadadoNoMes(YearMonth anoMes) {

        return this.usosDeVaga.stream()
                .filter(usoDeVaga -> usoDeVaga.entrouEntre(anoMes.atDay(1).atStartOfDay(), anoMes.atEndOfMonth().atTime(LocalTime.MIDNIGHT)))
                .mapToDouble(UsoDeVaga::valorPago)
                .sum();

    }

    /**
     * Retorna o total de usos do veículo.
     *
     * @return total de usos do veículo.
     */
    public int totalDeUsos() {

        return this.usosDeVaga.size();

    }

    /**
     * Histórico de uso de vaga deste veículo.
     *
     * @param de  data inicial de entrada
     * @param ate data final de entrada
     * @return histórico de usos de vaga deste veículo.
     */
    public List<UsoDeVaga> historico(LocalDate de, LocalDate ate) {

        Objects.requireNonNull(de);
        Objects.requireNonNull(ate);

        LocalDateTime deDateTime = de.atStartOfDay();
        LocalDateTime ateDateTime = ate.atTime(LocalTime.MIDNIGHT);

        return this.usosDeVaga.stream()
                .filter(usoDeVaga -> usoDeVaga.entrouEntre(deDateTime, ateDateTime))
                .toList();

    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Veiculo outro) {
            return this.placa.equals(outro.placa);
        }

        return false;

    }

    @Override
    public String toString() {
        return this.placa;
    }
}
