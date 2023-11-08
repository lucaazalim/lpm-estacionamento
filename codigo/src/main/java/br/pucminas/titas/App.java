package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.excecoes.VeiculoNaoEncontradoException;
import br.pucminas.titas.excecoes.VeiculoNaoEstaEstacionadoException;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Estacionamento> estacionamentos = new ArrayList<>();

    private static Estacionamento estacionamento;

    public static void main(String[] args) throws IOException {

        try {
            Serialization.carregar(estacionamentos::add);
        } catch (ClassNotFoundException e) {
            System.out.println("Houve um erro ao carregar os dados dos estacionamentos.");
            System.exit(1);
        }

        menu();

    }

    public static void menu() throws IOException {

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Salvar e sair");
        System.out.println("\t1. Criar estacionamento");
        System.out.println("\t2. Gerenciar estacionamento");

        int opcao;

        try {
            opcao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            opcao = -1;
        }

        boolean saindo = false;

        switch (opcao) {
            case 0 -> salvarESair();
            case 1 -> criarEstacionamento();
            case 2 -> gerenciarEstacionamento();

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

    public static void gerenciarEstacionamento() throws IOException {

        if (estacionamento == null) {

            System.out.println("Qual estacionamento você gostaria de gerenciar?");

            for (int i = 0; i < estacionamentos.size(); i++) {
                System.out.println((i + 1) + ". " + estacionamentos.get(i));
            }

            int estacionamentoSelecionado = Integer.parseInt(scanner.nextLine());

            if (estacionamentos.size() < estacionamentoSelecionado) {
                System.out.println("Esta opção de estacionamento não existe.");
                return;
            }

            estacionamento = estacionamentos.get(estacionamentoSelecionado - 1);

        }

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Voltar ao menu principal");
        System.out.println("\t1. Cadastrar cliente");
        System.out.println("\t2. Cadastrar veículo");
        System.out.println("\t3. Estacionar veículo");
        System.out.println("\t4. Sair da vaga");
        System.out.println("\t5. Consultar total");
        System.out.println("\t6. Consultar total no mês");
        System.out.println("\t7. Consultar valor médio");
        System.out.println("\t8. Mostrar top 5 clientes");
        System.out.println("\t9. Mostrar histórico do cliente");

        int opcao;

        try {
            opcao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            opcao = -1;
        }

        switch (opcao) {
            case 0 -> {
                estacionamento = null;
                menu();
                return;
            }
            case 1 -> cadastrarCliente();
            case 2 -> adicionarVeiculo();
            case 3 -> estacionarVeiculo();
            case 4 -> sairDaVaga();
            case 5 -> consultarTotalArrecadado();
            case 6 -> consultarTotalArrecadadoMes();
            case 7 -> consultarValorMedioPorUso();
            case 8 -> mostrarTop5Clientes();
            case 9 -> mostrarHistoricoCliente();

            default -> System.out.println("A opção informada é inválida.");
        }

        System.out.println();
        System.out.println("Pressione ENTER para voltar ao menu...");

        try {
            System.in.read();
        } catch (IOException ignored) {
        }

        gerenciarEstacionamento();

    }


    /**
     * Salva os dados e sai do programa
     *
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
        String nome = scanner.nextLine();

        System.out.println("Quantas fileiras tem o estacionamento?");
        int fileiras = Integer.parseInt(scanner.nextLine());

        System.out.println("Quantas vagas por fileira há no estacionamento?");
        int vagasPorFileira = Integer.parseInt(scanner.nextLine());

        Estacionamento estacionamento = new Estacionamento(nome, fileiras, vagasPorFileira);
        estacionamentos.add(estacionamento);

        System.out.println("Estacionamento '" + estacionamento + "' criado com sucesso!");

    }

    /**
     * Registra um novo cliente
     */
    public static void cadastrarCliente() {

        System.out.println("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        int proximoId = estacionamento.getClientes().size() + 1;

        Cliente cliente = new Cliente(proximoId, nomeCliente);
        System.out.println("Cliente registrado com o ID: " + cliente.getId());

        estacionamento.addCliente(cliente);

    }

    /**
     * Adiciona um veículo ao cliente
     */
    public static void adicionarVeiculo() {

        Cliente cliente = identificarCliente();

        if (cliente == null) {
            return;
        }

        System.out.println("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        Veiculo veiculo = new Veiculo(placa, cliente);
        cliente.addVeiculo(veiculo);

        System.out.println("Veículo adicionado ao cliente com sucesso.");

    }

    /**
     * Estaciona o veículo em uma vaga disponível
     */
    public static void estacionarVeiculo() {

        System.out.println("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        if (placa == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        try {
            estacionamento.estacionar(placa);
        } catch (VeiculoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Veículo estacionado com sucesso.");

    }

    /**
     * Retira o veículo da vaga que ele estava ocupando
     */
    public static void sairDaVaga() {

        System.out.println("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        if (placa == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        try {
            estacionamento.sair(placa);
            System.out.println("O veículo foi removido da vaga com sucesso.");
        } catch (ServicoNaoTerminadoException | VeiculoNaoEstaEstacionadoException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Exibe o total arrecadado pelo estacionamento
     */
    public static void consultarTotalArrecadado() {

        double total = estacionamento.totalArrecadado();
        System.out.println("O total arrecadado pelo estacionamento foi de R$ " + total);

    }

    /**
     * Exibe o total arrecadado pelo estacionamento em um determinado mês
     */
    public static void consultarTotalArrecadadoMes() {

        System.out.println("Informe o mês e ano desejados (MM/AAAA): ");
        YearMonth anoMes = YearMonth.parse(scanner.nextLine());

        double total = estacionamento.totalArrecadadoNoMes(anoMes);
        System.out.println("O total arrecadado em " + anoMes + " foi de R$ " + total);


    }

    /**
     * Exibe o valor médio de uso do estacionamento
     */
    public static void consultarValorMedioPorUso() {

        double valorMedio = estacionamento.valorMedioPorUso();
        System.out.println("O valor médio de uso no estacionamento foi de R$ " + valorMedio);

    }

    /**
     * Mostra os cinco clientes que mais geraram arrecadação em um determinado mês
     */
    public static void mostrarTop5Clientes() {
        // TO DO
    }

    /**
     * Mostra o histórico do cliente
     */
    public static void mostrarHistoricoCliente() {

        Cliente cliente = identificarCliente();

        System.out.println("Total de usos dos veículos: " + cliente.totalDeUsos());
        System.out.println("Arrecadação total do cliente: " + cliente.arrecadadoTotal());

    }

    /**
     * Procura cliente com o ID fornecido
     *
     * @return o cliente encontrado
     */
    private static Cliente identificarCliente() {

        System.out.println("Informe o ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine());

        Cliente cliente = estacionamento.getClientes().get(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        }

        return cliente;

    }

}
