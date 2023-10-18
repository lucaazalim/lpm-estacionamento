package br.pucminas.titas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import br.pucminas.titas.entidades.Vaga;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.excecoes.SairDeVagaDisponivelException;
import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.excecoes.VagaNaoDisponivelException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class VeiculoTest {
    
    private Veiculo veiculo;
    private Vaga vaga;

    @BeforeEach
    public void setUp(){
        veiculo = new Veiculo("ABC123");
        vaga = new Vaga("1",1);
    }

    @Test
    public void testEstacionar() throws VagaNaoDisponivelException {
        veiculo.estacionar(vaga);
        assertEquals(1,veiculo.totalDeUsos());
    }

    @Test
    public void testSair() throws VagaNaoDisponivelException, ServicoNaoTerminadoException, SairDeVagaDisponivelException {
        veiculo.estacionar(vaga);
        double valorPago = veiculo.sair();
        assertEquals(1, veiculo.totalDeUsos());
        assertTrue(valorPago >= 0);
    }

    @Test
    public void testTotalArrecadado() throws VagaNaoDisponivelException {
        veiculo.estacionar(vaga);
        veiculo.estacionar(vaga);
        double totalArrecadado = veiculo.totalArrecadado();
        assertEquals(2, veiculo.totalArrecadado());
        assertTrue(totalArrecadado >= 0);
    }

    @Test
    public void testArrecadadoNoMes() throws VagaNaoDisponivelException{
        veiculo.estacionar(vaga);
        veiculo.estacionar(vaga);
        double arrecadacaoNoMes = veiculo.arrecadadoNoMes(LocalDate.now().getMonthValue());
        assertTrue(arrecadacaoNoMes >= 0);
    }
}
