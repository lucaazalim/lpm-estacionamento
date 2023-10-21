package br.pucminas.titas;

import java.io.IOException;
import java.util.Scanner;

public class App {
    private static Scanner SCANNER;

    public static void main(String[] args) {

        SCANNER = new Scanner(System.in);

        menu();
    }

    public static void menu() throws IOException {
        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Salvar e sair");
        System.out.println("\t1. Cadastrar cliente");
        System.out.println("\t2. Adicionar veículo");
        System.out.println("\t3. Estacionar");
        System.out.println("\t4. Sair");
        System.out.println("\t5. Consultar total arrecadado pelo estacionamento");
        System.out.println("\t6. Consultar total arrecadado no mês");
        System.out.println("\t7. Consultar valor médio de utilização do estacionamento");
        System.out.println("\t8. Consultar top 5 clientes");

        int opcao;

        try {
            opcao = Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            opcao = -1;
        }
        boolean saindo = false;

        switch (opcao) {
            case 0 -> salvarESair();
            case 1 -> cadastrarCliente();
            case 2 -> adicionarVeiculo();
            case 3 -> estacionarVeiculo();
            case 4 -> sairDaVaga();
            case 5 -> consultarTotal();
            case 6 -> consultarTotalMes();
            case 7 -> consultarValorMedio();
            case 8 -> mostrarTop5Clientes();
            case 9 -> mostrarHistoricoCliente();
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

    public static void salvarESair() {
        // TO DO
    }
    public static void cadastrarCliente() {
        System.out.println("Digite o ID do cliente: ");
        String idCliente = SCANNER.nextLine();

        System.out.println("Digite o nome do cliente: ");
        String nomeCliente = SCANNER.nextLine();

        Cliente cliente = new Cliente(nomeCliente, idCliente);
    }

    public static void adicionarVeiculo() {
        System.out.println("Digite o ID do cliente: ");
        String idCliente;

        // Cliente cliente = Client.DATA.getById(clientId);

        System.out.println("Digite a placa do veículo: ");
        String placa;

        placa = SCANNER.nextLine();

        Veiculo veiculo = new Veiculo(placa);

        cliente.addVeiculo(veiculo);
    }

    public static void estacionarVeiculo() {
        System.out.println("Digite a placa do veículo: ");
        String placa;

        if (placa == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        //estacionamento.estacionar(placa);
    }

    public static void sairDaVaga() {
        System.out.println("Digite a placa do veículo: ");
        String placa;

        if (placa == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        //estacionamento.sair(placa);
    }

    public static void consultarTotal() {
        //double total = estacionamento.totalArrecadado();
        //System.out.println("O total arrecadado pelo estacionamento foi de R$" + total);
    }

    public static void consultarTotalMes() {
        //TO DO
    }

    public static void consultarValorMedio() {
        //double valorMedio = estacionamento.valorMedioPorUso();
        //System.out.println("O valor médio de uso no estacionamento foi de R$" +);
    }

    public static void mostrarTop5Clientes(); {
        // TO DO
    }

    public static void mostrarHistoricoCliente() {
        // TO DO
    }
}
