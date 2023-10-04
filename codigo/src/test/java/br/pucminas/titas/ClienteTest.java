package br.pucminas.titas;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;
    private Veiculo veiculo;
    private Vaga vaga;

    @BeforeAll
    public void setUp() {

        this.cliente = new Cliente("Luca Ferrari Azalim", "1");
        this.veiculo = new Veiculo("ABC-1234");
        this.vaga = new Vaga(1, 1);

        veiculo.estacionar(this.vaga);

    }

    @Test
    public void testPossuiVeiculo() {

        this.cliente.addVeiculo(veiculo);
        assertEquals(veiculo, this.cliente.possuiVeiculo("ABC-1234"));

    }

    @Test
    public void testTotalDeUsos() {

        assertEquals(1, this.cliente.totalDeUsos());

    }

    @Test
    public void testArrecadadoPorVeiculo() {

        // TODO assertEquals(0.0, this.cliente.arrecadadoPorVeiculo("ABC-1234"));

    }

    @Test
    public void testArrecadadoTotal() {

        // TODO assertEquals(0.0, this.cliente.arrecadadoTotal());

    }

    @Test
    public void testArrecadadoNoMes() {

        // TODO assertEquals(0.0, this.cliente.arrecadadoNoMes(1));

    }

}
