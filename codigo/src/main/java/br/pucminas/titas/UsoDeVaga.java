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
	private Servico[] servicos;

	public LocalDateTime getEntrada(){
		return LocalDateTime.from(this.entrada);
	}

	public UsoDeVaga(Vaga vaga) {
		if(!vaga.disponivel()){
			throw new IllegalArgumentException("Vaga não disponivel");
		}
		this.vaga = vaga;
		servicos = new Servico[2];
		entrada = LocalDateTime.now();
	}


	public double sair() {
		if (!podeSair()) {
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
		return valorPago + getServicoPrecoTotal();
	}

	public void addServico(Servico servico) throws IllegalArgumentException {
		int cont = 0;
		for (Servico s: servicos) {
			if ((s.getNome() == "Lavagem" && servico.getNome() == "Polimento") || (s.getNome() == "Polimento" && servico.getNome() == "Lavagem")) {
				throw new IllegalArgumentException("Vaga não disponivel");
			}
			cont++;
		}
		servicos[cont] = servico;
	}

	public double getServicoPrecoTotal() {
		double precoTotal = 0d;
		for (Servico servico: servicos) {
			precoTotal += servico.getPreco();
		}
		return precoTotal;
	}

	public boolean podeSair() {
		return true;
	}
}
