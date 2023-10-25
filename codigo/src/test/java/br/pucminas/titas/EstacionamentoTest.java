package br.pucminas.titas.entidades;

import br.pucminas.titas.excecoes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstacionamentoTest {

    private Estacionamento estacionamento;
    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    public void setUp() {
        estacionamento = new Estacionamento("Xulambs Parking", 10, 10);
        cliente = new Cliente("João", "12345");
        veiculo = new Veiculo("ABC-1234");
    }

    @Test
    public void testAddCliente() {
        estacionamento.addCliente(cliente);
        Cliente clienteEncontrado = estacionamento.encontrarCliente("12345");
        assertNotNull(clienteEncontrado, "Confere se cliente foi adicionado.");
    }

    @Test
    public void testAddVeiculo() {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo(veiculo, "12345");
        Veiculo veiculoEncontrado = estacionamento.procurarVeiculo("ABC-1234");
        assertEquals(veiculo, veiculoEncontrado, "Confere se veículo foi adicionado.");
    }

    @Test
    public void testEstacionar() {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo(veiculo, "12345");

        assertDoesNotThrow(() -> estacionamento.estacionar("ABC-1234"), "Erro ao estacionar o veículo");
    }

    @Test
    public void testSair() throws EstacionamentoLotadoException {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo(veiculo, "12345");
        estacionamento.estacionar("ABC-1234");

        assertDoesNotThrow(() -> estacionamento.sair("ABC-1234"), "Erro ao retirar o veículo");
    }

    @Test
    public void testTotalArrecadado() {
    }

    @Test
    public void testArrecadacaoNoMes() {
    }

    @Test
    public void testValorMedioPorUso() {
    }

}

