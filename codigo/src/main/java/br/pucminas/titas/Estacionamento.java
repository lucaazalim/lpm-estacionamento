package br.pucminas.titas;

public class Estacionamento {

	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;
	private Map<String, Vaga> veiculoVagaMap = new HashMap<>();

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
	        this.nome = nome;
	        this.id = new Cliente[100];
	        this.vagas = new Vaga[fileiras * vagasPorFila];
	        this.quantFileiras = fileiras;
	        this.vagasPorFileira = vagasPorFila;
		}

	public void addVeiculo(Veiculo veiculo, String idCli) {
		
	}

	public void addCliente(Cliente cliente) {
		
	}

	private void gerarVagas() {
		
	}

	public void estacionar(String placa) throws EstacionamentoLotadoException {
		boolean vagaEncontrada = false;
		for (Vaga vaga : vagas) {
        		if (vaga != null && vaga.disponivel()) {
            			vaga.estacionar();
            			veiculoVagaMap.put(placa, vaga);
				vagaEncontrada = true;
            			break;
        		}
    		}
		if (!vagaEncontrada) {
        		throw new EstacionamentoLotadoException("Não há vagas disponíveis para estacionar o veículo com placa " + placa);
    		}

	}

	public double sair(String placa) {
		Vaga vaga = veiculoVagaMap.get(placa);
    		if (vaga != null) {
        		vaga.sair();
        		veiculoVagaMap.remove(placa);
		}
	}

	public double totalArrecadado() {
		double total = 0;
        	for (Cliente cliente : id) {
            		total += cliente.arrecadadoTotal();
        	}
        	return total;
	}

	public double arrecadacaoNoMes(int mes) {
	        double total = 0;
        	for (Cliente cliente : id) {
            		total += cliente.arrecadadoNoMes(mes);
        	}
        	return total;
	}

	public double valorMedioPorUso() {
		
	}

	public String top5Clientes(int mes) {
		
	}

}
