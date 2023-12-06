package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Veiculo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteTest {

    private static Cliente cliente;

    @BeforeAll
    public static void setup() {
        cliente = new Cliente(1, "João");
    }

    @Test
    @Order(1)
    public void testCadastrarVeiculo() {

        Veiculo veiculo = new Veiculo("ABC123", cliente);

        assertDoesNotThrow(() -> cliente.cadastrarVeiculo(veiculo));

    }

    @Test
    @Order(2)
    public void testProcurarVeiculo() {

        assertNotNull(cliente.procurarVeiculo("ABC123"));

    }

    @Test
    public void testTotalDeUsos() {

        assertEquals(0, cliente.totalDeUsos());

    }

    @Test
    public void testArrecadadoTotal() {

        assertEquals(0, cliente.arrecadacaoTotal());

    }

    @Test
    public void testArrecadadoNoMes() {

        assertEquals(0, cliente.arrecadacaoNoMes(YearMonth.of(2023, 1)));

    }

    @Test
    public void testHistorico() {

        assertEquals(0, cliente.historico(LocalDate.MIN, LocalDate.MAX, null).size());

    }


}
