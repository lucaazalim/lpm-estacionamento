package br.pucminas.titas.entidades;

import br.pucminas.titas.excecoes.VeiculoJaSaiuException;
import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.excecoes.VagaNaoDisponivelException;
import br.pucminas.titas.excecoes.VeiculoNaoEstaEstacionadoException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

public class Veiculo implements Serializable {

    private final String placa;
    private final LinkedList<UsoDeVaga> usos;

    /**
     * Cria um novo veículo com a placa informada.
     *
     * @param placa Placa do veículo
     */
    public Veiculo(String placa) {
        this.placa = placa;
        this.usos = new LinkedList<>();
    }

    /**
     * Estaciona o veículo na vaga informada.
     *
     * @param vaga Vaga onde o veículo será estacionado
     * @throws VagaNaoDisponivelException
     */
    public void estacionar(Vaga vaga) throws VagaNaoDisponivelException {

        UsoDeVaga usoDeVaga = new UsoDeVaga(vaga);
        this.usos.add(usoDeVaga);

    }

    /**
     * Dispara {@see UsoDeVaga#sair()} no último uso de vaga do veículo.
     *
     * @return valor pago pelo veículo.
     * @throws ServicoNaoTerminadoException
     * @throws VeiculoNaoEstaEstacionadoException
     */
    public double sair() throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {

        UsoDeVaga usoDeVaga = this.usos.removeLast();

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

        return this.usos.stream().mapToDouble(UsoDeVaga::valorPago).sum();

    }

    /**
     * Retorna o total arrecadado com o veículo no mês informado.
     *
     * @param mes Mês a ser consultado
     * @return total arrecadado com o veículo no mês informado.
     */
    public double arrecadadoNoMes(int mes) {

        return this.usos.stream()
                .filter(usoDeVaga -> usoDeVaga.getEntrada().getMonthValue() == mes)
                .mapToDouble(UsoDeVaga::valorPago)
                .sum();

    }

    /**
     * Retorna o total de usos do veículo.
     *
     * @return total de usos do veículo.
     */
    public int totalDeUsos() {

        return this.usos.size();

    }

    /**
     * Histórico de uso de vaga deste veículo.
     *
     * @param de data inicial
     * @param ate data final
     * @return histórico de usos de vaga deste veículo.
     */
    public List<UsoDeVaga> historico(LocalDate de, LocalDate ate) {

        Objects.requireNonNull(de);
        Objects.requireNonNull(ate);

        LocalDateTime deDateTime = de.atStartOfDay();
        LocalDateTime ateDateTime = ate.atTime(LocalTime.MIDNIGHT);

        return this.usos.stream()
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
