package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.UsoDeVaga;
import br.pucminas.titas.entidades.Vaga;
import br.pucminas.titas.entidades.Veiculo;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VagaTest {

    private static Cliente cliente;
    private static Veiculo veiculo;
    private static Vaga vaga;

    @BeforeAll
    public static void setup() {
        cliente = new Cliente(1, "João");
        veiculo = new Veiculo("ABC-1234", cliente);
        vaga = new Vaga(0, 0);
    }

    @Test
    public void testDisponivel() {

        assertTrue(vaga.disponivel());

        vaga.estacionar(new UsoDeVaga(vaga, veiculo, null));

        assertFalse(vaga.disponivel());

    }

}
