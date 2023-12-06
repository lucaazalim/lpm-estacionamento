package br.pucminas.titas.entidades;

import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.excecoes.VeiculoNaoEstaEstacionadoException;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa um uso de vaga do estacionamento por um veículo.
 */
public class UsoDeVaga implements Serializable {

	private Vaga vaga;
	private Veiculo veiculo;
	private Servico servico;
	private LocalDateTime entrada, saida;

	/**
	 * Construtor base utilizado para criar um UsoDeVaga novo
	 * @param vaga onde o veículo está/será estacionado
	 * @param veiculo veículo a ser estacionado
	 * @param servico serviço solicitado pelo cliente
	 */
	public UsoDeVaga(Vaga vaga, Veiculo veiculo, Servico servico) {
		this(vaga, veiculo, servico, LocalDateTime.now(), null);
	}

	/**
	 * Contrutor para criação de UsoDeVaga antigos ou com saída e entrada definidos
	 * @param vaga onde o veículo está/será estacionado
	 * @param veiculo veículo a ser estacionado
	 * @param servico serviço solicitado pelo cliente
	 * @param entrada data da entrada do veículo
	 * @param saida data da saída do veículo
	 */
	public UsoDeVaga(Vaga vaga, Veiculo veiculo, Servico servico, LocalDateTime entrada, LocalDateTime saida) {

		Objects.requireNonNull(vaga);
		Objects.requireNonNull(veiculo);

		this.vaga = vaga;
		this.veiculo = veiculo;
		this.servico = servico;
		this.entrada = entrada;
		this.saida = saida;

        this.vaga.estacionar(this);

	}

	/**
	 * Retorna o veículo que está utilizando a vaga
	 * @return veículo
	 */
	public Veiculo getVeiculo() {
		return this.veiculo;
	}

	/**
	 * Retorna o serviço solicitado
	 * @return serviço
	 */
	public Servico getServico() {
		return this.servico;
	}

	/**
	 * Retorna a data de entrada do veículo
	 * @return data de entrada
	 */
	public LocalDateTime getEntrada() {
		return this.entrada;
	}

	/**
	 * Retorna a data de saída do veículo
	 * @return data de saída
	 */
	public LocalDateTime getSaida() {
		return this.saida;
	}

	/**
	 * Tenta liberar a vaga
	 *
	 * @return retorna o preço a ser pago pelo cliente
	 * @throws ServicoNaoTerminadoException ao tentar sair antes do serviço ser finalizado.
	 * @throws VeiculoNaoEstaEstacionadoException ao tentar sair sem que o veículo esteja estacionado.
	 */
	public double sair() throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {

		if (this.saida != null) {
			throw new VeiculoNaoEstaEstacionadoException();
		}

		if (!this.podeSair()) {
			throw new ServicoNaoTerminadoException();
		}

		this.saida = LocalDateTime.now();
		this.vaga.estacionar(null);

		return this.valorPago();

	}

	/**
	 * Confere se o veículo já saiu.
	 *
	 * @return true se o veículo já saiu, false caso contrário.
	 */
	public boolean saiu() {
		return this.saida != null;
	}

	/**
	 * Calcula o valor total a ser pago pelo cliente.
	 *
	 * @return valor total a ser pago pelo cliente.
	 */
	public double valorPago() {

		if(this.saida == null) {
			return 0;
		}

		double valorPago = this.veiculo.getCliente().getPlano().calcularValor(this.entrada, this.saida);

		if(this.servico != null) {
			valorPago += servico.getPreco();
		}

		return valorPago;

	}

	/**
	 * Confere se o cliente pode sair.
	 *
	 * @return true se o cliente pode sair, false caso contrário.
	 */
	public boolean podeSair() {

		if(this.servico == null) {
			return true;
		}

		Duration duration = Duration.between(this.entrada, LocalDateTime.now());
		return duration.toHours() >= this.servico.getHorasMinimas();

	}

	/**
	 * Confere se o cliente entrou entre as datas informadas.
	 *
	 * @param de data inicial de entrada
	 * @param ate data final de entrada
	 * @return true se o cliente entrou entre as datas informadas, false caso contrário.
	 */
	public boolean entrouEntre(LocalDateTime de, LocalDateTime ate) {

		Objects.requireNonNull(de);
		Objects.requireNonNull(ate);

		return this.entrada.isAfter(de) && this.entrada.isBefore(ate);

	}

	@Override
	public String toString() {
		return "Local estacionado: " + this.vaga + "\n- Placa do veículo: " + this.veiculo + "\n- Serviço: " + (this.servico != null ? this.servico : "Nenhum") + "\n- Entrada: " + this.entrada + "\n- Saída: " + this.saida + "\n- Valor pago: R$" + this.valorPago();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UsoDeVaga usoDeVaga) {
			return vaga.equals(usoDeVaga.vaga) && veiculo.equals(usoDeVaga.veiculo) && servico == usoDeVaga.servico;
		}
		return false;
	}
}
