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

	public UsoDeVaga(Vaga vaga, Veiculo veiculo, Servico servico) {
		this(vaga, veiculo, servico, LocalDateTime.now(), null);
	}

	public UsoDeVaga(Vaga vaga, Veiculo veiculo, Servico servico, LocalDateTime entrada, LocalDateTime saida) {

		Objects.requireNonNull(vaga);
		Objects.requireNonNull(veiculo);

		this.vaga = vaga;
		this.veiculo = veiculo;
		this.servico = servico;
		this.entrada = entrada;
		this.saida = saida;

	}

	public Veiculo getVeiculo() {
		return this.veiculo;
	}

	public Servico getServico() {
		return this.servico;
	}

	public LocalDateTime getEntrada() {
		return this.entrada;
	}

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
		this.vaga.estacionar(this);

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
		return "Local estacionado: " + this.vaga + "\n- Placa do veículo: " + this.veiculo + "\n- Serviço: " + (this.servico != null ? this.servico : "Nenhum") + "\n- Entrada: " + this.entrada + "\n- Saída: " + this.saida + "\n- Valor pago: R$ " + this.valorPago();
	}
}
