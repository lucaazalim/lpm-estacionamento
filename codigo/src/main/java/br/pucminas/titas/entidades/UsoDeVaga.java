package br.pucminas.titas.entidades;

import br.pucminas.titas.excecoes.VeiculoJaSaiuException;
import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.excecoes.VagaNaoDisponivelException;
import br.pucminas.titas.enums.Servico;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class UsoDeVaga implements Serializable {

	private Vaga vaga;
	private Veiculo veiculo;
	private Servico servico;
	private LocalDateTime entrada, saida;

	public UsoDeVaga(Vaga vaga, Veiculo veiculo, Servico servico) throws VagaNaoDisponivelException {

		Objects.requireNonNull(vaga);
		Objects.requireNonNull(veiculo);

		if(!vaga.disponivel()){
			throw new VagaNaoDisponivelException(vaga);
		}

		this.vaga = vaga;
		this.veiculo = veiculo;
		this.servico = servico;
		entrada = LocalDateTime.now();

	}

	/**
	 * Tenta liberar a vaga
	 * @return retorna o preço a ser pago pelo cliente
	 * @throws ServicoNaoTerminadoException
	 * @throws VeiculoJaSaiuException
	 */
	public double sair() throws ServicoNaoTerminadoException, VeiculoJaSaiuException {

		if (this.saida != null) {
			throw new VeiculoJaSaiuException();
		}

		if (!podeSair(LocalDateTime.now())) {
			throw new ServicoNaoTerminadoException();
		}

		saida = LocalDateTime.now();	
		vaga.sair();
		return valorPago();

	}

	/**
	 * Calcula o valor total a ser pago baseado na diferença entre o tempo de entrada e de saida,
	 * os serviços contratados a o preço por fração
	 * @return retorna o valor total a ser pago
	 */
	public double valorPago() {

		if(this.saida == null) {
			return 0;
		}

		return this.veiculo.getCliente().getPlano().calcularValor(this.entrada, this.saida);

	}

	/**
	 * Confere se o tempo mínimo for passado
	 * @param saida horário da saída
	 * @return
	 */
	public boolean podeSair(LocalDateTime saida) {

		if(this.servico == null) {
			return true;
		}

		Duration duration = Duration.between(this.entrada, saida);
		return duration.toHours() >= this.servico.getHorasMinimas();

	}

	/* 
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
		return this.vaga + " - " + this.veiculo + " - " + this.servico + " - " + this.entrada + " - " + this.saida;
	}
}
