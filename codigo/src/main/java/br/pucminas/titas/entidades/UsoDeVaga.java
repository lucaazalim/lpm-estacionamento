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

public class UsoDeVaga implements Serializable {

	private static final double VALOR_FRACAO = 4.0;
	private static final double VALOR_MAXIMO = 50.0;
	private Vaga vaga;
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private double valorPago;
	private Servico servico;

	public UsoDeVaga(Vaga vaga) throws VagaNaoDisponivelException {
		init(vaga, null);
	}

	public UsoDeVaga(Vaga vaga, Servico servico) throws VagaNaoDisponivelException {
		init(vaga, servico);
	}

	/**
	 * Um construtor padrão
	 * @param vaga
	 * @param servico
	 * @throws VagaNaoDisponivelException
	 */
	private void init(Vaga vaga, Servico servico) throws VagaNaoDisponivelException {

		if(!vaga.disponivel()){
			throw new VagaNaoDisponivelException("Vaga não disponivel");
		}

		this.vaga = vaga;
		this.servico = servico;
		entrada = LocalDateTime.now();
	}

	/**
	 * Pega o data e hora da entrada do cliente
	 * @return
	 */
	public LocalDateTime getEntrada(){
		return LocalDateTime.from(this.entrada);
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
			throw new ServicoNaoTerminadoException("Os serviços solicitados ainda não foram concluídos");
		}

		saida = LocalDateTime.now();	
		vaga.sair();
		return valorPago();

	}

	/**
	 * Calcula o valor total a ser pago
	 * @return retorna o valor total a ser pago
	 */
	public double valorPago() {

		if(this.saida == null) {
			return 0;
		}

		Duration duration = Duration.between(this.entrada, this.saida);

		double minutos = duration.toMinutes();
		double fracaoMinutos = Math.floor(minutos / 15);

		this.valorPago = fracaoMinutos * VALOR_FRACAO;

		if(this.valorPago > VALOR_MAXIMO){
			this.valorPago = VALOR_MAXIMO;
		}

		if(this.servico != null) {
			this.valorPago += this.getServicoPrecoTotal();
		}

		return this.valorPago;

	}

	/**
	 * Adiciona um serviço ou troca o serviço atual por um novo
	 * @param servico Serviço ser escolhido
	 */
	public void adicionaServico(Servico servico) {
		this.servico = servico;
	}

	/**
	 * Pega o preço do serviço solicitado
	 * @return retorna o preço do serviço solicitado
	 */
	public double getServicoPrecoTotal() {
		return servico.getPreco();
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

	/**
	 * Confere se o cliente entrou entre as datas informadas.
	 *
	 * @param de data inicial ou null caso não queira definir uma data inicial
	 * @param ate data final ou null caso não queira definir uma data final
	 * @return true se o cliente entrou entre as datas informadas, false caso contrário.
	 */
	public boolean entrouEntre(LocalDate de, LocalDate ate) {
		return (de == null || this.entrada.isAfter(de.atStartOfDay()))
				&& (ate == null || this.entrada.isBefore(ate.atTime(LocalTime.MIDNIGHT)));
	}

}
