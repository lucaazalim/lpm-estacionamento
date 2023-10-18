package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstacionamentoTest {

    private Estacionamento estacionamento;
    private static final String ID = "1";
    private static final String NOME = "João";
    private static final String PLACA = "PUZ-5654";

    @BeforeEach
    public void setUp() {
        estacionamento = new Estacionamento("Xulambs Parking", 5, 20);
        Cliente cliente = new Cliente(NOME,ID);
        estacionamento.addCliente(cliente);
        Veiculo veiculo = new Veiculo(PLACA);
        estacionamento.addVeiculo(veiculo, ID);
    }

    @Test
    public void testEstacionar() {
        assertDoesNotThrow(() -> estacionamento.estacionar(PLACA),"confere se estacionar não lança exceção");
    }

    @Test
    public void testEstacionamentoLotado() {
        for (int i = 0; i < 100; i++) {
            try {
                estacionamento.estacionar("ABC-" + i);
            } catch (Estacionamento.EstacionamentoLotadoException e) {
                fail("Estacionamento lotado antes do previsto.");
            }
        }
        assertThrows(Estacionamento.EstacionamentoLotadoException.class, () -> estacionamento.estacionar(PLACA),"Confere se estacionar quando lotado lança exceção.");
    }

    @Test
    public void testSair() {
        try {
            estacionamento.estacionar(PLACA);
        } catch (Estacionamento.EstacionamentoLotadoException e) {
            fail("Estacionamento lotado, teste incompleto.");
        }
        assertDoesNotThrow(() -> estacionamento.sair(PLACA),"confere se sair não lança exceções");
    }


    @Test
    public void testTotalArrecadado() {
        double total = estacionamento.totalArrecadado();
        assertTrue(total >= 0,"Confere se valor não é negativo");
    }

    @Test
    public void testArrecadacaoNoMes() {
        double total = estacionamento.arrecadacaoNoMes(10);
        assertTrue(total >= 0,"Confere se valor não é negativo");
    }

}
