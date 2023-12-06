package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Vaga;
import br.pucminas.titas.entidades.Veiculo;
import static org.junit.jupiter.api.Assertions.*;

import br.pucminas.titas.excecoes.VeiculoJaEstacionadoException;
import br.pucminas.titas.excecoes.VeiculoNaoEstaEstacionadoException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VeiculoTest {

    private static Cliente cliente;
    private static Veiculo veiculo;
    private static Vaga vaga;

    @BeforeAll
    public static void setup() {
        cliente = new Cliente(1, "João");
        veiculo = new Veiculo("ABC123", cliente);
        vaga = new Vaga(0, 0);
    }

    @Test
    @Order(1)
    public void testEstacionar() {

        assertDoesNotThrow(
                () -> veiculo.estacionar(vaga, null),
                "Garantindo que nenhuma exceção é lançada ao estacionar."
        );

        assertThrows(
                VeiculoJaEstacionadoException.class,
                () -> veiculo.estacionar(vaga, null),
                "Testando exceção ao estacionar veículo já estacionado."
        );

        assertFalse(vaga.disponivel(), "Garantindo que vaga não está disponível após estacionar.");

    }

    @Test
    @Order(2)
    public void testSair() {

        assertDoesNotThrow(veiculo::sair, "Garantindo que nenhuma exceção é lançada ao sair.");

        assertThrows(
                VeiculoNaoEstaEstacionadoException.class,
                veiculo::sair,
                "Testando exceção ao sair com um veículo que já saiu."
        );

        assertTrue(vaga.disponivel());

    }

    @Test
    @Order(3)
    public void testTotalArrecadado() {

        assertEquals(0, veiculo.totalArrecadado());

    }

    @Test
    @Order(4)
    public void testArrecadadoNoMes() {

        assertEquals(0, veiculo.arrecadadoNoMes(YearMonth.of(2023, 1)));

    }

    @Test
    @Order(5)
    public void testTotalDeUsos()  {

        assertEquals(1, veiculo.totalDeUsos());

    }

    @Test
    @Order(6)
    public void testHistorico() {

        assertEquals(1, veiculo.historico(LocalDate.MIN, LocalDate.MAX, null).size());

    }

}
