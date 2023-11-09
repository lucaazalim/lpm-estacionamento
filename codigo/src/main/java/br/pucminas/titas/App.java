package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.excecoes.*;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            System.out.println("Salvando dados...");

            try {
                Serialization.salvar(estacionamentos);
                System.out.println("Dados salvos com sucesso.");
            } catch (IOException e) {
                System.err.println("Ocorreu um erro ao salvar os dados.");
            }

        }));

        menu();

    }

    public static void menu() throws IOException {

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Sair");
        System.out.println("\t1. Cadastrar estacionamento");
        System.out.println("\t2. Gerenciar estacionamento");

        try {

            int opcao = lerNumero();

            switch (opcao) {
                case 0 -> System.exit(0);
                case 1 -> cadastrarEstacionamento();
                case 2 -> gerenciarEstacionamento();
                default -> System.out.println("A opção informada é inválida.");
            }

        } catch (AppExcecao e) {
            System.err.println(e.getMessage());
        }

        pressioneEnterParaVoltar();
        menu();

    }

    public static void gerenciarEstacionamento() throws IOException, AppExcecao {

        if (estacionamento == null) {

            if (estacionamentos.isEmpty()) {
                System.out.println("Não há estacionamentos cadastrados.");
                return;
            }

            System.out.println("Qual estacionamento você deseja gerenciar?");

            for (int i = 0; i < estacionamentos.size(); i++) {
                System.out.println((i + 1) + ". " + estacionamentos.get(i));
            }

            int estacionamentoSelecionado = lerNumero();

            if (estacionamentos.size() < estacionamentoSelecionado) {
                System.out.println("Esta opção de estacionamento não existe.");
                return;
            }

            estacionamento = estacionamentos.get(estacionamentoSelecionado - 1);

        }

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Voltar ao menu principal");
        System.out.println("\t1. Cadastrar cliente");
        System.out.println("\t2. Estacionar veículo");
        System.out.println("\t3. Sair da vaga");
        System.out.println("\t4. Consultar total arrecadado");
        System.out.println("\t5. Consultar total arrecadado no mês");
        System.out.println("\t6. Consultar valor médio por uso");
        System.out.println("\t7. Consultar top 5 clientes");
        System.out.println("\t8. Consultar histórico de um cliente");

        try {

            int opcao = lerNumero();

            switch (opcao) {
                case 0 -> {
                    estacionamento = null;
                    menu();
                    return;
                }
                case 1 -> cadastrarCliente();
                case 2 -> estacionarVeiculo();
                case 3 -> sairDaVaga();
                case 4 -> consultarTotalArrecadado();
                case 5 -> consultarTotalArrecadadoMes();
                case 6 -> consultarValorMedioPorUso();
                case 7 -> consultarTopClientes();
                case 8 -> consultarHistoricoCliente();
                default -> System.out.println("A opção informada é inválida.");
            }

        } catch (AppExcecao e) {
            System.err.println(e.getMessage());
        }

        pressioneEnterParaVoltar();
        gerenciarEstacionamento();

    }

    public static void pressioneEnterParaVoltar() {

        System.out.println();
        System.out.println("Pressione ENTER para voltar ao menu...");

        try {
            System.in.read();
        } catch (IOException ignored) {
        }

    }

    /**
     * Cria um estacionamento, com nome, número de fileiras e quantidade de vagas por fileira.
     */
    public static void cadastrarEstacionamento() throws AppExcecao {

        System.out.println("Qual é o nome do estacionamento?");
        String nome = scanner.nextLine();

        System.out.println("Quantas fileiras tem o estacionamento?");
        int fileiras = lerNumero();

        System.out.println("Quantas vagas por fileira há no estacionamento?");
        int vagasPorFileira = lerNumero();

        Estacionamento estacionamento = new Estacionamento(nome, fileiras, vagasPorFileira);
        estacionamentos.add(estacionamento);

        System.out.println("Estacionamento '" + estacionamento + "' criado com sucesso!");

    }

    /**
     * Registra um novo cliente
     */
    public static void cadastrarCliente() {

        System.out.println("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        int proximoId = estacionamento.getClientes().size() + 1;

        Cliente cliente = new Cliente(proximoId, nome);
        System.out.println("Cliente registrado com sucesso: " + cliente);

        estacionamento.addCliente(cliente);

    }

    /**
     * Adiciona um veículo ao cliente
     */
    public static void cadastrarVeiculo() throws AppExcecao {

        Cliente cliente = lerCliente();

        System.out.println("Informe a placa do veículo: ");
        String placa = scanner.nextLine();

        Veiculo veiculo = new Veiculo(placa, cliente);
        cliente.addVeiculo(veiculo);

        System.out.println("Veículo adicionado ao cliente com sucesso.");

    }

    /**
     * Estaciona o veículo em uma vaga disponível
     */
    public static void estacionarVeiculo() throws AppExcecao {

        Cliente cliente = lerCliente();

        System.out.println("Informe a placa do veículo: ");
        String placa = scanner.nextLine();

        System.out.println("Informe o serviço que o veículo irá utilizar: ");
        System.out.println("\t0. Nenhum");

        for (Servico servico : Servico.values()) {
            System.out.println("\t" + servico.ordinal() + ". " + servico);
        }

        int servicoSelecionado = lerNumero();

        if (servicoSelecionado < 0 || servicoSelecionado > Servico.values().length) {
            throw new AppExcecao("A opção de serviço informada é inválida.");
        }

        Servico servico = Servico.values()[servicoSelecionado];
        Veiculo veiculo = cliente.possuiVeiculo(placa);

        if (veiculo == null) {
            veiculo = new Veiculo(placa, cliente);
            cliente.addVeiculo(veiculo);

            System.out.println("Veículo cadastrado e adicionado ao cliente com sucesso.");
        }

        try {
            estacionamento.estacionar(placa, servico);
        } catch (VeiculoNaoEncontradoException | EstacionamentoLotadoExcecao | VagaNaoDisponivelException e) {
            throw new AppExcecao(e.getMessage());
        }

        System.out.println("Veículo estacionado com sucesso.");

    }

    /**
     * Retira o veículo da vaga que ele estava ocupando
     */
    public static void sairDaVaga() throws AppExcecao {

        System.out.println("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        try {
            double valorPago = estacionamento.sair(placa);
            System.out.println("O veículo foi removido da vaga com sucesso. O valor a ser pago é R$ " + valorPago + ".");
        } catch (VeiculoNaoEncontradoException | ServicoNaoTerminadoException | VeiculoNaoEstaEstacionadoException |
                 VeiculoJaSaiuException e) {
            throw new AppExcecao(e.getMessage());
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
    public static void consultarTotalArrecadadoMes() throws AppExcecao {

        YearMonth anoMes = lerAnoMes();

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
    public static void consultarTopClientes() throws AppExcecao {

        YearMonth anoMes = lerAnoMes();

        System.out.println("Informe a quantidade limite de clientes: ");
        int limite = lerNumero();

        List<Cliente> clientes = estacionamento.topClientes(anoMes, limite);

        System.out.println("Os " + limite + " clientes que mais utilizaram o estacionamento em " + anoMes + " foram: ");

        for (int i = 0; i < clientes.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + clientes.get(i));
        }

    }

    /**
     * Mostra o histórico do cliente
     */
    public static void consultarHistoricoCliente() throws AppExcecao {

        Cliente cliente = lerCliente();

        System.out.println("Total de usos de todos os veículos do cliente: " + cliente.totalDeUsos());
        System.out.println("Arrecadação total do cliente: " + cliente.arrecadadoTotal());

    }

    private static Cliente lerCliente() throws AppExcecao {

        System.out.println("Informe o ID ou nome do cliente: ");
        String idOuNome = scanner.nextLine();

        Cliente cliente;

        try {
            int id = Integer.parseInt(idOuNome);
            cliente = estacionamento.getClientes().get(id);
        } catch (NumberFormatException e) {
            cliente = estacionamento.getClientes().values().stream()
                    .filter(c -> c.getNome().equals(idOuNome))
                    .findFirst().orElse(null);
        }

        if (cliente == null) {
            throw new AppExcecao("O cliente informado não foi encontrado.");
        }

        return cliente;

    }

    private static Integer lerNumero() throws AppExcecao {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new AppExcecao("O valor informado deve ser um número válido.");
        }
    }

    private static YearMonth lerAnoMes() throws AppExcecao {

        System.out.println("Informe o mês e ano desejados (mês/ano): ");

        try {
            return YearMonth.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("MM/yyyy"));
        } catch (DateTimeParseException e) {
            throw new AppExcecao("O valor informado deve estar no formato mês/ano.");
        }

    }

}
