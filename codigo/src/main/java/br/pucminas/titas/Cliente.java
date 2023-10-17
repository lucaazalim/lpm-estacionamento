package br.pucminas.titas;

public class Cliente {

    private String nome;
    private String id;
    private Veiculo[] veiculos;
    private int contadorVeiculos = 0;

    /**
     * Constrói um novo objeto Cliente com o nome e id fornecidos.
     * Inicializa um array vazio de veículos com um tamanho de 100.
     *
     * @param nome O nome do cliente.
     * @param id O identificador único do cliente.
     */
    public Cliente(String nome, String id) {
        this.nome = nome;
        this.id = id;
        this.veiculos = new Veiculo[100]; // limite de 100 veículos
    }

    /**
     * Adiciona um veículo à coleção do cliente.
     *
     * @param veiculo O veículo a ser adicionado.
     */
    public void addVeiculo(Veiculo veiculo) {
        if (contadorVeiculos < veiculos.length) {
            veiculos[contadorVeiculos] = veiculo;
            contadorVeiculos++;
        }
    }

    /**
     * Verifica se o cliente possui um veículo com uma determinada placa.
     *
     * @param placa A placa para verificar.
     * @return O veículo com a placa correspondente, ou null se não encontrado.
     */
    public Veiculo possuiVeiculo(String placa) {
        Veiculo qual = new Veiculo(placa);
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null && veiculo.equals(qual)) {
                return veiculo;
            }
        }
        return null;
    }

    /**
     * Calcula o total de usos de todos os veículos do cliente.
     *
     * @return O total de usos de todos os veículos.
     */
    public int totalDeUsos() {
        int total = 0;
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                total += veiculo.totalDeUsos();
            }
        }
        return total;
    }

    /**
     * Recupera o montante total arrecadado por um veículo específico.
     *
     * @param placa A placa do veículo.
     * @return O montante total arrecadado pelo veículo, ou 0 se o veículo não for encontrado.
     */
    public double arrecadadoPorVeiculo(String placa) {
        Veiculo veiculo = possuiVeiculo(placa);
        if (veiculo != null) {
            return veiculo.totalArrecadado();
        }
        return 0;
    }

    /**
     * Calcula o montante total arrecadado por todos os veículos do cliente.
     *
     * @return O montante total arrecadado por todos os veículos.
     */
    public double arrecadadoTotal() {
        double total = 0;
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                total += veiculo.totalArrecadado();
            }
        }
        return total;
    }

    /**
     * Calcula o montante arrecadado por todos os veículos do cliente em um determinado mês.
     *
     * @param mes O mês a ser considerado.
     * @return O montante total arrecadado no mês especificado.
     */
    public double arrecadadoNoMes(int mes) {
        double total = 0;
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                total += veiculo.arrecadadoNoMes(mes);
            }
        }
        return total;
    }
}
