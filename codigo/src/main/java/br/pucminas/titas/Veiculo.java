package br.pucminas.titas;

public class Veiculo {

	private static final int MAX_USOS = 1000;

	private final String placa;
	private final UsoDeVaga[] usos;

	/**
	 * Cria um novo veículo com a placa informada.
	 *
	 * @param placa Placa do veículo
	 */
	public Veiculo(String placa) {
		this.placa = placa;
		this.usos = new UsoDeVaga[MAX_USOS];
	}

	/**
	 * Estaciona o veículo na vaga informada.
	 *
	 * @param vaga Vaga onde o veículo será estacionado
	 * @throws VagaNaoDisponivelException
	 */
	public void estacionar(Vaga vaga) throws VagaNaoDisponivelException {

		for(int i = 0; i < this.usos.length; i++) {

			if(this.usos[i] == null) {
				this.usos[i] = new UsoDeVaga(vaga);
				break;
			}

		}

	}

	/**
	 * Dispara {@see UsoDeVaga#sair()} no último uso de vaga do veículo.
	 *
	 * @return valor pago pelo veículo.
	 * @throws SairDeVagaDisponivelException
	 * @throws ServicoNaoTerminadoException
	 */
	public double sair() throws ServicoNaoTerminadoException, SairDeVagaDisponivelException {
		return this.usos[this.usos.length - 1].sair();
	}

	/**
	 * Retorna o total arrecadado com o veículo.
	 *
	 * @return total arrecadado com o veículo.
	 */
	public double totalArrecadado() {

		double totalArrecadado = 0;

		for(UsoDeVaga usoDeVaga : this.usos) {
			totalArrecadado += usoDeVaga.valorPago();
		}

		return totalArrecadado;

	}

	/**
	 * Retorna o total arrecadado com o veículo no mês informado.
	 *
	 * @param mes Mês a ser consultado
	 * @return total arrecadado com o veículo no mês informado.
	 */
	public double arrecadadoNoMes(int mes) {

		double arrecadadoNoMes = 0;

		for(UsoDeVaga usoDeVaga : this.usos) {

			if(usoDeVaga.getEntrada().getMonthValue() == mes) {
				arrecadadoNoMes += usoDeVaga.valorPago();
			}

		}

		return arrecadadoNoMes;

	}

	/**
	 * Retorna o total de usos do veículo.
	 *
	 * @return total de usos do veículo.
	 */
	public int totalDeUsos() {

		int totalDeUsos = 0;

		for(UsoDeVaga usoDeVaga : this.usos) {
			if(usoDeVaga != null) {
				totalDeUsos++;
			}
		}

		return totalDeUsos;
	}

}
