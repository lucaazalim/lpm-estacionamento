package br.pucminas.titas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsoDeVagaTest {
    private Vaga vaga;

    @BeforeEach
    public void setUp() {
        vaga = new Vaga("Y", 2);
    }

    @Test
    public void tentarUsarUmaVagaEmUso() {
        vaga.estacionar();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new UsoDeVaga(vaga);
        }, "Vaga não disponivel");

        assertEquals("Vaga não disponivel", e.getMessage(), "Testa se uma exeção é lançada quando a vaga não estiver disponível");
    }

    @Test
    public void pegarOValorTotal() {
        UsoDeVaga u = new UsoDeVaga(vaga);
        
    }
}
