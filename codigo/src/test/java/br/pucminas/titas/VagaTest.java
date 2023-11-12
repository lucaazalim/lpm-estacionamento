package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Vaga;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VagaTest {

    private static Cliente cliente;
    private static Vaga vaga;

    @BeforeAll
    public static void setup() {
        cliente = new Cliente(1, "João");
        vaga = new Vaga(0, 0);
    }

    @Test
    public void testDisponivel() {

        assertTrue(vaga.disponivel());

        vaga.setDisponivel(false);

        assertFalse(vaga.disponivel());

    }

}
