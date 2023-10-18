package br.pucminas.titas.entidades;

import br.pucminas.titas.excecoes.SairDeVagaDisponivelException;
import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.excecoes.VagaNaoDisponivelException;
import br.pucminas.titas.enums.Servico;

import java.time.Duration;
import java.time.LocalDateTime;

public class UsoDeVaga {

	private static final double FRACAO_USO = 0.25;
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
	 * @throws SairDeVagaDisponivelException
	 */
	public double sair() throws ServicoNaoTerminadoException, SairDeVagaDisponivelException {
		if (vaga.disponivel()) {
			throw new SairDeVagaDisponivelException("Essa vaga não está em uso");
		}
		else if (!podeSair(LocalDateTime.now())) {
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
		Duration duration = Duration.between(entrada, saida);
		double minutos = duration.toMinutes();
		double fracaoMinutos = Math.floor(minutos / 15);
		valorPago = fracaoMinutos * VALOR_FRACAO;
		if(valorPago > VALOR_MAXIMO){
			valorPago = VALOR_MAXIMO;
		}
		valorPago += getServicoPrecoTotal();
		return valorPago;
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
		Duration duration = Duration.between(entrada, saida);
		return duration.toHours() >= servico.getHoraMinimas();
	}
}
