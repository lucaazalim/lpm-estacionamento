package br.pucminas.titas;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private Cliente cliente;
    private Veiculo veiculo;
    private Vaga vaga;

    @BeforeEach
    public void setUp() {

        cliente = new Cliente("João", "1");
        veiculo = new Veiculo("ABC-1234");
        vaga = new Vaga(1, 1);

        veiculo.estacionar(vaga);

    }

    @Test
    public void testAddVeiculo() {
        cliente.addVeiculo(veiculo);
        assertNotNull(cliente.possuiVeiculo("ABC-1234"),"Testando adição de veículos ao cliente.");
    }

    @Test
    public void testPossuiVeiculo() {
        cliente.addVeiculo(veiculo);
        assertNotNull(cliente.possuiVeiculo("ABC-1234"),"Testando posse de veículo do cliente.");
        assertNull(cliente.possuiVeiculo("PUZ-5654"),"Testando que cliente não possui veículo que não foi adicionado.");
    }

    @Test
    public void testTotalDeUsos() {
        cliente.addVeiculo(veiculo);
        assertEquals(15, cliente.totalDeUsos(),"Testando total de usos de um cliente.");
    }

    @Test
    public void testArrecadadoPorVeiculo() {
        cliente.addVeiculo(veiculo);
        assertEquals(4.0, cliente.arrecadadoPorVeiculo("ABC-1234"), "Testando total arrecadado por veículo do cliente.");
    }

    @Test
    public void testArrecadadoTotal() {
        cliente.addVeiculo(veiculo);
        assertEquals(4.0, cliente.arrecadadoTotal(),"Testando total arrecadado por cliente.");
    }

}
