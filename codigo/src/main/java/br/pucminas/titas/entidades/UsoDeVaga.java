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
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private double valorPago;
	private Servico servico;

	public UsoDeVaga(Vaga vaga, Veiculo veiculo) throws VagaNaoDisponivelException {
		init(vaga, veiculo, null);
	}

	public UsoDeVaga(Vaga vaga, Veiculo veiculo, Servico servico) throws VagaNaoDisponivelException {
		init(vaga, veiculo, servico);
	}

	/**
	 * Um construtor padrão
	 * @param vaga
	 * @param servico
	 * @throws VagaNaoDisponivelException
	 */
	private void init(Vaga vaga, Veiculo veiculo, Servico servico) throws VagaNaoDisponivelException {

		Objects.requireNonNull(vaga);
		Objects.requireNonNull(veiculo);

		if(!vaga.disponivel()){
			throw new VagaNaoDisponivelException("Vaga não disponivel");
		}

		this.vaga = vaga;
		this.veiculo = veiculo;
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
	 * Calcula o valor total a ser pago baseado na diferença entre o tempo de entrada e de saida,
	 * os serviços contratados a o preço por fração
	 * @return retorna o valor total a ser pago
	 */
	public double valorPago() {

		if(this.saida == null) {
			return 0;
		}

		return this.veiculo.getCliente().getPlano().valorPago(this.entrada, this.saida);

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
	 * Indica quando um UsoDeVaga é igual ao outro caso: 
	 * a vaga for igual e os horários de entrada e saida coincidirem
	 @param u objeto de refência
	 @return true se o objeto for igual ao objeto comparado
	 */
	public boolean equals(UsoDeVaga u){
		boolean resp = false;
		if(u != null && getClass() == u.getClass()){
			if(this==u) resp = true;
			else{
				if((vaga == u.vaga && entrada != u.getEntrada())
				&& entrada == u.entrada
				&& saida == u.saida
				) resp = true;
			}//procurar igualdade
		} 
		return resp;		
	}

	/**
	 * Transforma o objeto UsoDeVaga em string retornando:
	 * a vaga, horário de saida, horário de saida, valor pago e serviços contratados 
	 * @return 
	 */
	public String toString(){
		return "Uso de vagas {" +
				"vaga: " + this.vaga +
				", entrda: " + this.entrada +
				", saida: " + this.saida + 
				", valor pago: " + this.valorPago +
				", serviços: " + this.servico +
				"}";
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

}
