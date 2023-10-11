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

	public LocalDateTime getEntrada(){
		return this.entrada;
	}

	public UsoDeVaga(Vaga vaga) {
		if(!vaga.disponivel()){
			throw new IllegalArgumentException("Vaga não disponivel");
		}
		entrada = LocalDateTime.now();
	}


	public double sair() {
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
		return valorPago;
	}
}
