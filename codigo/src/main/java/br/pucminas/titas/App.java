package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.Plano;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.enums.TipoPlano;
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

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_ANO_MES = DateTimeFormatter.ofPattern("MM/yyyy");

    private static final List<Estacionamento> ESTACIONAMENTOS = new ArrayList<>();

    private static Estacionamento estacionamento;

    public static void main(String[] args) throws IOException {

        try {
            Serialization.carregar(ESTACIONAMENTOS::add);
        } catch (ClassNotFoundException e) {
            System.out.println("Houve um erro ao carregar os dados dos estacionamentos.");
            System.exit(1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            System.out.println("Salvando dados...");

            try {
                Serialization.salvar(ESTACIONAMENTOS);
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

            if (ESTACIONAMENTOS.isEmpty()) {
                throw new AppExcecao("Não há estacionamentos cadastrados.");
            }

            System.out.println("Qual estacionamento você deseja gerenciar?");

            for (int i = 1; i <= ESTACIONAMENTOS.size(); i++) {
                System.out.println("\t" + i + ". " + ESTACIONAMENTOS.get(i - 1));
            }

            int opcao = lerNumero();

            if (ESTACIONAMENTOS.size() < opcao) {
                throw new AppExcecao("Esta opção de estacionamento não existe.");
            }

            estacionamento = ESTACIONAMENTOS.get(opcao - 1);

        }

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Voltar ao menu principal");
        System.out.println("\t1. Cadastrar um cliente");
        System.out.println("\t2. Alterar plano de um cliente");
        System.out.println("\t3. Estacionar um veículo");
        System.out.println("\t4. Retirar veículo de uma vaga");
        System.out.println("\t5. Consultar total arrecadado");
        System.out.println("\t6. Consultar total arrecadado no mês");
        System.out.println("\t7. Consultar valor médio por uso");
        System.out.println("\t8. Consultar top 5 clientes");
        System.out.println("\t9. Consultar histórico de um cliente");

        try {

            int opcao = lerNumero();

            switch (opcao) {
                case 0 -> {
                    estacionamento = null;
                    menu();
                    return;
                }
                case 1 -> cadastrarCliente();
                case 2 -> alterarPlanoCliente();
                case 3 -> estacionarVeiculo();
                case 4 -> sairDaVaga();
                case 5 -> consultarTotalArrecadado();
                case 6 -> consultarTotalArrecadadoMes();
                case 7 -> consultarValorMedioPorUso();
                case 8 -> consultarTopClientes();
                case 9 -> consultarHistoricoCliente();
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
        String nome = SCANNER.nextLine();

        System.out.println("Quantas fileiras tem o estacionamento?");
        int fileiras = lerNumero();

        System.out.println("Quantas vagas por fileira há no estacionamento?");
        int vagasPorFileira = lerNumero();

        Estacionamento estacionamento = new Estacionamento(nome, fileiras, vagasPorFileira);
        ESTACIONAMENTOS.add(estacionamento);

        System.out.println("Estacionamento '" + estacionamento + "' criado com sucesso!");

    }

    /**
     * Registra um novo cliente
     */
    public static void cadastrarCliente() {

        System.out.println("Informe o nome do cliente: ");
        String nome = SCANNER.nextLine();

        int proximoId = estacionamento.getClientes().size() + 1;

        Cliente cliente = new Cliente(proximoId, nome);
        System.out.println("Cliente registrado com sucesso: " + cliente);

        estacionamento.addCliente(cliente);

    }

    public static void alterarPlanoCliente() throws AppExcecao {

        Cliente cliente = lerCliente();
        TipoPlano tipoPlano = lerEnum(TipoPlano.class, false);

        assert tipoPlano != null;

        Plano plano = tipoPlano.get();
        cliente.setPlano(plano);

        System.out.println("O plano do cliente '" + cliente.getNome() + "' foi alterado para '" + plano + "'.");

    }

    /**
     * Estaciona o veículo em uma vaga disponível
     */
    public static void estacionarVeiculo() throws AppExcecao {

        Cliente cliente = lerCliente();

        System.out.println("Informe a placa do veículo: ");
        String placa = SCANNER.nextLine();

        Servico servico = lerEnum(Servico.class, true);
        Veiculo veiculo = Optional.ofNullable(cliente.procurarVeiculo(placa)).orElseGet(() -> new Veiculo(placa, cliente));

        cliente.cadastrarVeiculo(veiculo);

        try {
            estacionamento.estacionar(veiculo, servico);
        } catch (EstacionamentoLotadoExcecao | VagaNaoDisponivelException e) {
            throw new AppExcecao(e.getMessage());
        }

        System.out.print("Veículo estacionado!");

        if(servico != null) {
            System.out.print(" O serviço '" + servico + "' será realizado.");
        }

        System.out.println();

    }

    /**
     * Retira o veículo da vaga que ele estava ocupando
     */
    public static void sairDaVaga() throws AppExcecao {

        System.out.println("Digite a placa do veículo: ");
        String placa = SCANNER.nextLine();

        try {
            double valorPago = estacionamento.sair(placa);
            System.out.println("O veículo foi removido da vaga e o valor a ser pago é R$ " + valorPago + ".");
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
        System.out.println("O total arrecadado em " + anoMes.format(FORMATO_ANO_MES) + " foi de R$ " + total);


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

        System.out.println("Os " + limite + " clientes que mais utilizaram o estacionamento em " + anoMes.format(FORMATO_ANO_MES) + " foram: ");

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
        String idOuNome = SCANNER.nextLine();

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
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            throw new AppExcecao("O valor informado deve ser um número válido.");
        }
    }

    private static YearMonth lerAnoMes() throws AppExcecao {

        System.out.println("Informe o mês e ano desejados (MM/AAAA): ");

        try {
            return YearMonth.parse(SCANNER.nextLine(), FORMATO_ANO_MES);
        } catch (DateTimeParseException e) {
            throw new AppExcecao("O valor informado deve estar no formato MM/AAAA.");
        }

    }

    private static <T extends Enum<T>> T lerEnum(Class<T> enumClass, boolean opcaoNenhum) throws AppExcecao {

        System.out.println("Informe a opção desejada: ");

        if(opcaoNenhum) {
            System.out.println("\t0. Nenhum");
        }

        for (T enumConstant : enumClass.getEnumConstants()) {
            System.out.println("\t" + (enumConstant.ordinal() + 1) + ". " + enumConstant);
        }

        int opcao = lerNumero();

        if(opcao == 0 && opcaoNenhum) {
            return null;
        }

        return enumClass.getEnumConstants()[opcao - 1];

    }

}
