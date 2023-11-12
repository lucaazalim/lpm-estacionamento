package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.UsoDeVaga;
import br.pucminas.titas.entidades.Vaga;
import br.pucminas.titas.entidades.Veiculo;

import static org.junit.jupiter.api.Assertions.*;

import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.enums.TipoPlano;
import br.pucminas.titas.excecoes.ServicoNaoTerminadoException;
import br.pucminas.titas.excecoes.VeiculoJaEstacionadoException;
import br.pucminas.titas.excecoes.VeiculoNaoEstaEstacionadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

public class UsoDeVagaTest {

    private Vaga vaga;
    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    public void setup() {
        vaga = new Vaga(0, 0);
        cliente = new Cliente(1, "João");
        veiculo = new Veiculo("ABC123", cliente);
    }

    @Test
    public void testSairJaSaido() throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {

        UsoDeVaga usoDeVaga = new UsoDeVaga(vaga, veiculo, null);
        usoDeVaga.sair();

        assertThrows(VeiculoNaoEstaEstacionadoException.class, usoDeVaga::sair);

    }

    @Test
    public void testSairComServicoNaoTerminado() {

        UsoDeVaga usoDeVaga = new UsoDeVaga(vaga, veiculo, Servico.LAVAGEM);

        assertThrows(ServicoNaoTerminadoException.class, usoDeVaga::sair);

    }

    @Test
    public void testSaiu() throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {

        UsoDeVaga usoDeVaga = new UsoDeVaga(vaga, veiculo, null);
        usoDeVaga.sair();

        assertTrue(usoDeVaga.saiu());

    }

    @Test
    public void testValorPago() {

        UsoDeVaga usoDeVaga = new UsoDeVaga(
                vaga,
                veiculo,
                null,
                LocalDateTime.of(2023, 1, 1, 9, 0, 0),
                LocalDateTime.of(2023, 1, 1, 16, 0, 0)
        );

        assertEquals(50, usoDeVaga.valorPago(), "Testando valor pago por um horista.");

        cliente.setPlano(TipoPlano.TURNISTA_MANHA.get());

        assertEquals(0, usoDeVaga.valorPago(), "Testando valor pago por um turnista (manhã).");

        cliente.setPlano(TipoPlano.TURNISTA_NOITE.get());

        assertEquals(50, usoDeVaga.valorPago(), "Testando valor pago por um turnista (noite).");

        cliente.setPlano(TipoPlano.MENSALISTA.get());

        assertEquals(0, usoDeVaga.valorPago(), "Testando valor pago por um mensalista.");

    }

    @Test
    public void testPodeSair() {

        UsoDeVaga usoDeVaga = new UsoDeVaga(
                vaga,
                veiculo,
                Servico.POLIMENTO,
                LocalDateTime.of(2023, 1, 1, 9, 0, 0),
                null
        );

        assertTrue(usoDeVaga.podeSair());

    }

    @Test
    public void testEntrouEntre() {

        UsoDeVaga usoDeVaga = new UsoDeVaga(
                vaga,
                veiculo,
                Servico.POLIMENTO,
                LocalDateTime.of(2023, 1, 1, 9, 0, 0),
                null
        );

        assertTrue(usoDeVaga.entrouEntre(
                LocalDateTime.of(2022, 12, 30, 10, 0, 0),
                LocalDateTime.of(2023, 1, 2, 10, 0, 0)
        ));

        assertFalse(usoDeVaga.entrouEntre(
                LocalDateTime.of(2021, 12, 30, 10, 0, 0),
                LocalDateTime.of(2022, 1, 2, 10, 0, 0)
        ));

    }

}
