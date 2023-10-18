package br.pucminas.titas;

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

	public LocalDateTime getEntrada(){
		return LocalDateTime.from(this.entrada);
	}

	public UsoDeVaga(Vaga vaga) {
		init(vaga, null);
	}

	public UsoDeVaga(Vaga vaga, Servico servico) {
		init(vaga, servico);
	}

	private void init(Vaga vaga, Servico servico) throws IllegalArgumentException {
		if(!vaga.disponivel()){
			throw new IllegalArgumentException("Vaga não disponivel");
		}
		this.vaga = vaga;
		this.servico = servico;
		entrada = LocalDateTime.now();
	}

	public double sair() {
		if (vaga.disponivel()) {
			throw new IllegalArgumentException("Essa vaga não está em uso");
		}
		else if (!podeSair()) {
			throw new IllegalArgumentException("Os serviços solicitados ainda não foram concluídos");
		}
		saida = LocalDateTime.now();	
		vaga.sair();
		return valorPago();
	}

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

	public void adicionaServico(Servico servico) {
		this.servico = servico;
	}

	public double getServicoPrecoTotal() {
		return servico.getPreco();
	}

	public boolean podeSair() {
		Duration duration = Duration.between(entrada, saida);
		return duration.toHours() >= servico.getHoraMinimas();
	}
}
