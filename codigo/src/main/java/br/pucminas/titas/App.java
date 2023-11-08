package br.pucminas.titas;

import java.io.IOException;
import java.util.Scanner;

import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.excecoes.EstacionamentoLotadoException;
import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.excecoes.VeiculoNaoEstaEstacionadoException;

public class App {

    private static Estacionamento[] estacionamentos = new Estacionamento[3];

    private static Scanner SCANNER;

    public static void main(String[] args) throws IOException, EstacionamentoLotadoException, ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {

        SCANNER = new Scanner(System.in);

        menu();
    }

    /**
     * Mostra o menu e lê a entrada do usuário.
     */
    public static void menu() throws IOException, EstacionamentoLotadoException, ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {
        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Salvar e sair");
        System.out.println("\t1. Criar estacionamento");
        System.out.println("\t2. Cadastrar cliente");
        System.out.println("\t3. Cadastrar veículo");
        System.out.println("\t4. Estacionar veículo");
        System.out.println("\t5. Sair da vaga");
        System.out.println("\t6. Consultar total");
        System.out.println("\t7. Consultar total no mês");
        System.out.println("\t8. Consultar valor médio");
        System.out.println("\t9. Mostrar top 5 clientes");
        System.out.println("\t10. Mostrar histórico do cliente");

        int opcao;

        try {
            opcao = Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            opcao = -1;
        }
        boolean saindo = false;

        switch (opcao) {
            case 0 -> salvarESair();
            case 1 -> criarEstacionamento();
            case 2 -> cadastrarCliente();
            case 3 -> adicionarVeiculo();
            case 4 -> estacionarVeiculo();
            case 5 -> sairDaVaga();
            case 6 -> consultarTotal();
            case 7 -> consultarTotalMes();
            case 8 -> consultarValorMedio();
            case 9 -> mostrarTop5Clientes();
            case 10 -> mostrarHistoricoCliente();
            
            default -> System.out.println("A opção informada é inválida.");
        }

        if (!saindo) {

            System.out.println();
            System.out.println("Pressione ENTER para voltar ao menu...");

            try {
                System.in.read();
            } catch (IOException ignored) {
            }

            menu();

        }
    }

    /**
     * Salva os dados e sai do programa
     * @throws IOException
     */
    public static void salvarESair() throws IOException {
        Serialization.salvar(estacionamentos);
        System.exit(0);
    }

    /**
     * Cria um estacionamento, com nome, número de fileiras e quantidade de vagas por fileira.
     */
    public static void criarEstacionamento() {
        System.out.println("Qual é o nome do estacionamento?");
        String nome = SCANNER.nextLine();

        System.out.println("Quantas fileiras tem o estacionamento?");
        int fileiras = SCANNER.nextInt();

        System.out.println("Quantas vagas por fileira há no estacionamento?");
        int vagasPorFila = SCANNER.nextInt();

        Estacionamento estacionamento = new Estacionamento(nome, fileiras, vagasPorFila);
        estacionamentos[estacionamentos.length] = estacionamento;
    }

    /**
     * Registra um novo cliente
     */
    public static void cadastrarCliente() {
        Estacionamento estacionamento = identificarEstacionamento();
        System.out.println("Digite o nome do cliente: ");
        String nomeCliente = SCANNER.nextLine();

        Cliente cliente = new Cliente(nomeCliente);
        System.out.println("Cliente registrado com o ID: " + cliente.getId());
    }

    /**
     * Procura por um dos estacionamentos
     * @return o estacionamento encontrado
     */
    private static Estacionamento identificarEstacionamento() {
        System.out.println("Qual estacionamento você gostaria de usar?");
        String nome = SCANNER.nextLine();

        for (int i = 0; i < 3; i++) {
            if (nome == estacionamentos[i].getNome()) {
                return estacionamentos[i];
            } else {
                System.out.println("Estacionamento não encontrado!");
            }
        }
        return null;
    }

    /**
     * Adiciona um veículo ao cliente
     */
    public static void adicionarVeiculo() {
        Cliente cliente = identificarCliente(identificarEstacionamento());

        System.out.println("Digite a placa do veículo: ");
        String placa;

        placa = SCANNER.nextLine();

        Veiculo veiculo = new Veiculo(placa, cliente);

        cliente.addVeiculo(veiculo);
    }

    /**
     * Procura cliente com o ID fornecido
     * @return o cliente encontrado
     */
    private static Cliente identificarCliente(Estacionamento estacionamento) {
        System.out.println("Digite o ID do cliente");
        int id = SCANNER.nextInt();
        Cliente[] clientes = estacionamento.getClientes();
        for (int j = 0; j < clientes.length; j++) {
            if (id == clientes[j].getId()) {
                return clientes[j];

            } else {
                System.out.println("Cliente não encontrado!");
            }
        }
        return null;
    }

    /**
     * Estaciona o veículo em uma vaga disponível
     */
    public static void estacionarVeiculo() throws EstacionamentoLotadoException {
        Estacionamento estacionamento = identificarEstacionamento();
        System.out.println("Digite a placa do veículo: ");
        String placa = SCANNER.nextLine();

        if (placa == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        estacionamento.estacionar(placa);
    }

    /**
     * Retira o veículo da vaga que ele estava ocupando
     */
    public static void sairDaVaga() throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {
        Estacionamento estacionamento = identificarEstacionamento();
        System.out.println("Digite a placa do veículo: ");
        String placa = SCANNER.nextLine();

        if (placa == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        estacionamento.sair(placa);
    }

    /**
     * Exibe o total arrecadado pelo estacionamento
     */
    public static void consultarTotal() {
        Estacionamento estacionamento = identificarEstacionamento();
        double total = estacionamento.totalArrecadado();
        System.out.println("O total arrecadado pelo estacionamento foi de R$" + total);
    }

    /**
     * Exibe o total arrecadado pelo estacionamento em um determinado mês
     */
    public static void consultarTotalMes() {
        // TO DO
    }

    /**
     * Exibe o valor médio de uso do estacionamento
     */
    public static void consultarValorMedio() {
        Estacionamento estacionamento = identificarEstacionamento();
        double valorMedio = estacionamento.valorMedioPorUso();
        System.out.println("O valor médio de uso no estacionamento foi de R$" + valorMedio);
    }

    /**
     * Mostra os cinco clientes que mais geraram arrecadação em um determinado mês
     */
    public static void mostrarTop5Clientes(){
        // TO DO
    }

    /**
     * Mostra o histórico do cliente
     */
    public static void mostrarHistoricoCliente() {
        Cliente cliente = identificarCliente(identificarEstacionamento());
        System.out.println("Total de usos dos veículos: " + cliente.totalDeUsos());
        System.out.println("Arrecadação total do cliente: " + cliente.arrecadadoTotal());
    }
}
