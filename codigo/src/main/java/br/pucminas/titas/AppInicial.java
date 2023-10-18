package br.pucminas.titas;

public class AppInicial {
    public static void main(String[] args) {
        /**
        * Cria um estacionamento com 2 fileiras e 5 vagas por fileira
        */
        Estacionamento estacionamento = new Estacionamento("Xulambs Parking 1", 2, 5);

        /** 
        * Cria clientes
        */
        Cliente cliente1 = new Cliente("João", "123");
        Cliente cliente2 = new Cliente("Maria", "456");

        /** 
        * Adiciona veículos aos clientes
        */
        Veiculo veiculo1 = new Veiculo("ABC123");
        Veiculo veiculo2 = new Veiculo("XYZ789");

        cliente1.addVeiculo(veiculo1);
        cliente2.addVeiculo(veiculo2);

        /**
        * Adiciona clientes ao estacionamento
        */
        estacionamento.addCliente(cliente1);
        estacionamento.addCliente(cliente2);

        try {
            /**
            * Estaciona veículos
            */
            estacionamento.estacionar("ABC123");
            estacionamento.estacionar("XYZ789");

            /**
            * Faz os veículos saírem do estacionamento
            */
            estacionamento.sair("ABC123");
            estacionamento.sair("XYZ789");

            /**
            * Calcula o total arrecadado
            */
            double totalArrecadado = estacionamento.totalArrecadado();
            System.out.println("Total arrecadado: R$" + totalArrecadado);
        } catch (Estacionamento.EstacionamentoLotadoException e) {
            System.out.println("Estacionamento lotado: " + e.getMessage());
        }
    }
}
