package br.pucminas.titas;

/**
 * Representa um estacionamento com fileiras definidas e vagas por fileira.
 * Fornece métodos para gerenciar veículos, clientes e vagas de estacionamento.
 */
public class Estacionamento {

    private String nome;

    private Cliente[] id;

    private Vaga[] vagas;

    private int quantFileiras;

    private int vagasPorFileira;

    /**
     * Cria um novo estacionamento com o nome, número de fileiras e vagas por fileira especificados.
     *
     * @param nome O nome do estacionamento.
     * @param fileiras O número de fileiras no estacionamento.
     * @param vagasPorFila O número de vagas por fileira no estacionamento.
     */
    public Estacionamento(String nome, int fileiras, int vagasPorFila) {
        this.nome = nome;
        this.quantFileiras = fileiras;
        this.vagasPorFileira = vagasPorFila;
        this.id = new Cliente[0];
        this.vagas = new Vaga[0];
        gerarVagas();
    }

    /**
     * Adiciona um veículo para um cliente especificado no estacionamento.
     *
     * @param veiculo O veículo a ser adicionado.
     * @param idCli O ID do cliente proprietário do veículo.
     */
    public void addVeiculo(Veiculo veiculo, String idCli) {
        Cliente cliente = encontrarCliente(idCli);
        if (cliente != null) {
            cliente.addVeiculo(veiculo);
        }
    }

    /**
     * Localiza um cliente no estacionamento usando o ID do cliente fornecido.
     *
     * @param idCli O ID do cliente a ser localizado.
     * @return O objeto cliente, se encontrado, caso contrário, retorna null.
     */
    private Cliente encontrarCliente(String idCli) {
		return null;
	}

    /**
     * Adiciona um cliente ao estacionamento.
     *
     * @param cliente O cliente a ser adicionado.
     */
    public void addCliente(Cliente cliente) {
        Cliente[] novosClientes = new Cliente[id.length + 1];
        System.arraycopy(id, 0, novosClientes, 0, id.length);
        novosClientes[id.length] = cliente;
        id = novosClientes;
    }

    /**
     * Gera vagas de estacionamento com base no número de fileiras e vagas por fileira.
     */
    private void gerarVagas() {
        int totalVagas = quantFileiras * vagasPorFileira;
        vagas = new Vaga[totalVagas];
        for (int i = 0; i < totalVagas; i++) {
            vagas[i] = new Vaga("Y" + (i / vagasPorFileira) + (i % vagasPorFileira));
        }
    }
}

   

