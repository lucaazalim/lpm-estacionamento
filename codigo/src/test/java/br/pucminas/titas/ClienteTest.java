package br.pucminas.titas;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private Cliente cliente;
    private Veiculo veiculo1;
    private Veiculo veiculo2;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("João", "1");
        veiculo1 = new Veiculo("ABC-1234");

        veiculo1.setArrecadacao(100.0);
        veiculo1.setUsoTotal(5);

        veiculo2 = new Veiculo("PUZ-5654");
        veiculo2.setArrecadacao(200.0);
        veiculo2.setUsoTotal(10);
    }

    @Test
    public void testAddVeiculo() {
        cliente.addVeiculo(veiculo1);
        assertNotNull(cliente.possuiVeiculo("ABC-1234")); //confirma que cliente pode adicionar veiculos
    }

    @Test
    public void testPossuiVeiculo() {
        cliente.addVeiculo(veiculo1);
        assertNotNull(cliente.possuiVeiculo("ABC-1234")); //confirma que cliente possui veiculo1
        assertNull(cliente.possuiVeiculo("PUZ-5654")); //confirma que cliente nao possui veiculo2
    }

    @Test
    public void testTotalDeUsos() {
        cliente.addVeiculo(veiculo1);
        cliente.addVeiculo(veiculo2);
        assertEquals(15, cliente.totalDeUsos()); //confirma total de usos dos veiculos
    }

    @Test
    public void testArrecadadoPorVeiculo() {
        cliente.addVeiculo(veiculo1);
        assertEquals(100.0, cliente.arrecadadoPorVeiculo("ABC-1234")); //confirma arrecadado veiculo1
        assertEquals(0.0, cliente.arrecadadoPorVeiculo("PUZ-5654")); //confirma que veiculo2 nao teve arrecadacao
    }

    @Test
    public void testArrecadadoTotal() {
        cliente.addVeiculo(veiculo1);
        cliente.addVeiculo(veiculo2);
        assertEquals(300.0, cliente.arrecadadoTotal()); //confirma o arrecadado dos dois veiculos
    }

}
