package br.pucminas.titas;

import br.pucminas.titas.entidades.Vaga;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VagaTest {
    private Vaga vaga;

    @BeforeEach
    public void setUp() {
        vaga = new Vaga("A", 1);
    }

    @Test
    public void testEstacionar() {
        assertTrue(vaga.estacionar());
        assertFalse(vaga.disponivel());
    }

    @Test
    public void testEstacionarVagaJaOcupada() {
        vaga.estacionar(); 
        assertFalse(vaga.estacionar()); 
        assertFalse(vaga.disponivel());
    }

    @Test
    public void testSair() {
        vaga.estacionar(); 
        assertTrue(vaga.sair()); 
        assertTrue(vaga.disponivel());
    }

    @Test
    public void testSairVagaJaLivre() {
        vaga.estacionar();
        assertTrue(vaga.sair()); 
        assertTrue(vaga.disponivel());
    }

    @Test
    public void testDisponivel() {
        assertTrue(vaga.disponivel());
        vaga.estacionar(); 
        assertFalse(vaga.disponivel());
        vaga.sair(); 
        assertTrue(vaga.disponivel());
    }
}
