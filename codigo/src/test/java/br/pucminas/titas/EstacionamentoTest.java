package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.Plano;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.excecoes.EstacionamentoLotadoException;
import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.excecoes.VeiculoJaEstacionadoException;
import br.pucminas.titas.excecoes.VeiculoNaoEstaEstacionadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

public class EstacionamentoTest {

    private Estacionamento estacionamento;
    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    void setUp() {

        estacionamento = new Estacionamento("Xulambs Parking", 1, 2);
        cliente = estacionamento.cadastrarCliente("Luca");
        veiculo = new Veiculo("PUZ5654", cliente);
        cliente.cadastrarVeiculo(veiculo);

        cliente.setPlano(new Plano("Teste") {
            @Override
            public double calcularValor(LocalDateTime entrada, LocalDateTime saida) {
                return 500;
            }
        });

    }

    @Test
    public void testGetClientes() {
        assertEquals(1, estacionamento.getClientes().size());
    }

    @Test
    public void testGetVagas() {
        assertEquals(2, estacionamento.getVagas().size());
    }

    @Test
    public void testCadastrarCliente() {
        assertDoesNotThrow(() -> estacionamento.cadastrarCliente("João"), "Deve cadastrar um cliente sem lançar exceção");
        assertEquals(2, estacionamento.getClientes().size(), "Deve haver um único cliente cadastrado");
    }

    @Test
    public void testEstacionar() {

        assertDoesNotThrow(() -> estacionamento.estacionar(veiculo, null), "Deve estacionar um veículo sem lançar exceção");

        assertThrows(VeiculoJaEstacionadoException.class, () ->
                        estacionamento.estacionar(veiculo, null),
                "Deve lançar exceção ao tentar estacionar um veículo já estacionado"
        );

        assertDoesNotThrow(
                () -> estacionamento.estacionar(new Veiculo("ABC1234", cliente), null),
                "Deve estacionar outro veículo sem lançar exceção"
        );

        assertThrows(EstacionamentoLotadoException.class,
                () -> estacionamento.estacionar(new Veiculo("VBG233", cliente), null),
                "Deve lançar exceção ao tentar estacionar um veículo em um estacionamento lotado"
        );

    }

    @Test
    public void testProcurarVeiculo() {

        assertEquals(veiculo, estacionamento.procurarVeiculo("PUZ5654"), "Deve encontrar o veículo estacionado");
        assertNull(estacionamento.procurarVeiculo("ABC1234"), "Não deve encontrar um veículo não estacionado");

    }

    @Test
    public void testSair() throws EstacionamentoLotadoException, VeiculoJaEstacionadoException {

        assertThrows(VeiculoNaoEstaEstacionadoException.class, () -> estacionamento.sair(veiculo), "Deve lançar exceção ao tentar remover um veículo não estacionado");

        estacionamento.estacionar(veiculo, null);

        assertDoesNotThrow(() -> estacionamento.sair(veiculo), "Deve remover o veículo estacionado sem lançar exceção");

        estacionamento.estacionar(veiculo, Servico.LAVAGEM);

        assertThrows(ServicoNaoTerminadoException.class, () -> estacionamento.sair(veiculo), "Deve lançar exceção ao tentar remover um veículo com serviço não terminado");

    }

    @Test
    public void testTotalArrecadado() {

        helperEstacionarESair();

        assertEquals(500, estacionamento.totalArrecadado());

    }

    @Test
    public void testArrecadadoNoMes() {

        helperEstacionarESair();

        assertEquals(500, estacionamento.totalArrecadadoNoMes(YearMonth.now()));

    }

    @Test
    public void testValorMedioPorUso() {

        helperEstacionarESair();

        assertEquals(500, estacionamento.valorMedioPorUso());

    }

    @Test
    public void testTopClientes() {

        helperEstacionarESair();

        assertTrue(estacionamento.topClientes(YearMonth.now(), 1).contains(cliente));

    }

    @Test
    public void testQuantidadeMediaDeUsos() {

        helperEstacionarESair();

        assertEquals(1, estacionamento.quantidadeMediaDeUsos(YearMonth.now(), cliente.getPlano()));

    }

    @Test
    public void testArrecadacaoMediaPorCliente() {

        helperEstacionarESair();

        assertEquals(500, estacionamento.arrecadacaoMediaPorCliente(YearMonth.now(), cliente.getPlano()));

    }

    public void helperEstacionarESair() {
        try {
            estacionamento.estacionar(veiculo, null);
            estacionamento.sair(veiculo);
        } catch (Exception e) {
            fail("Não deve lançar exceção");
        }
    }

}