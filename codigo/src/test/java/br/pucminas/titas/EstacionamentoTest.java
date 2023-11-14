package br.pucminas.titas;

import br.pucminas.titas.entidades.Cliente;
import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.excecoes.EstacionamentoLotadoException;
import br.pucminas.titas.excecoes.VeiculoJaEstacionadoException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EstacionamentoTest {

    private Estacionamento estacionamento;
    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        estacionamento = new Estacionamento("Xulambs Parking", 10, 10);
        cliente = new Cliente(1, "Guilherme");
        veiculo = new Veiculo("PUZ5654", cliente);
    }

    @Test
    void testCadastrarCliente() {
        assertDoesNotThrow(() -> estacionamento.cadastrarCliente("Guilherme"),"Afere se cadastro de cliente não lança exceção.");
    }

    @Test
    void testGetClientes() {
        assertNotNull(estacionamento.getClientes(),"Afere se método não retorna nulo.");
    }
    @Test
    void testEstacionar() throws EstacionamentoLotadoException, VeiculoJaEstacionadoException {
        assertDoesNotThrow(() -> estacionamento.estacionar(veiculo,Servico.LAVAGEM),"Afere se estacionar não lança exceção.");
    }
    @Test
    void testGetVagas() {
        assertNotNull(estacionamento.getVagas(),"Afere se método não retorna nulo.");
    }


}