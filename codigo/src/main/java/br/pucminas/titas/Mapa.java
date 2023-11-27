package br.pucminas.titas;

import br.pucminas.titas.entidades.Estacionamento;
import br.pucminas.titas.entidades.UsoDeVaga;
import br.pucminas.titas.entidades.Vaga;
import br.pucminas.titas.entidades.Veiculo;
import br.pucminas.titas.enums.Servico;
import br.pucminas.titas.excecoes.EstacionamentoLotadoException;
import br.pucminas.titas.excecoes.VeiculoJaEstacionadoException;
import br.pucminas.titas.observador.Observador;

import javax.swing.*;
import java.awt.*;

public class Mapa extends JFrame {

    public static void main(String[] args) throws EstacionamentoLotadoException, VeiculoJaEstacionadoException {
        Estacionamento estacionamento = new Estacionamento("Estacionamento", 10, 10);
        estacionamento.estacionar(new Veiculo("ABC-1234", estacionamento.cadastrarCliente("João")), Servico.POLIMENTO);
        Mapa mapa = new Mapa(estacionamento);
        mapa.setVisible(true);
    }

    public Mapa(Estacionamento estacionamento) {

        super("Mapa do \"" + estacionamento.getNome() + "\"");

        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new GridLayout(estacionamento.getLinhas(), estacionamento.getColunas(), 3, 3));

        for (int linha = 0; linha < estacionamento.getLinhas(); linha++) {
            for (int coluna = 0; coluna < estacionamento.getColunas(); coluna++) {

                Vaga vaga = estacionamento.procurarVaga(linha, coluna);
                add(new MapaVaga(vaga));

            }
        }

    }

    public static class MapaVaga extends JButton implements Observador {

        private final Vaga vaga;

        public MapaVaga(Vaga vaga) {

            super("");

            this.vaga = vaga;
            this.vaga.observar(this);

            this.configurar();

        }

        public void configurar() {

            StringBuilder textoBotao = new StringBuilder();

            textoBotao.append("<html><center>");

            textoBotao.append(this.vaga);

            textoBotao.append("</center></html>");

            setText(textoBotao.toString());

            setOpaque(true);
            setBorderPainted(false);
            setFocusPainted(false);
            setBackground(this.vaga.disponivel() ? Color.GREEN : Color.RED);

            addActionListener(e -> {

                StringBuilder textoModal = new StringBuilder();

                textoModal.append("<html>");

                if (this.vaga.disponivel()) {
                    textoModal.append("Vaga disponível.");
                } else {

                    UsoDeVaga usoDeVaga = this.vaga.getUsoDeVaga();

                    textoModal
                            .append("Vaga ocupada!")
                            .append("<br><br>")
                            .append("<b>Placa: </b>")
                            .append(usoDeVaga.getVeiculo().getPlaca())
                            .append("<br><b>Cliente: </b>")
                            .append(usoDeVaga.getVeiculo().getCliente().getNome())
                            .append("<br>");

                    if(usoDeVaga.getServico() != null) {
                        textoModal.append("<br><b>Serviço: </b>")
                                .append(usoDeVaga.getServico());
                    }

                    textoModal
                            .append("<br><b>Entrada: </b>")
                            .append(usoDeVaga.getEntrada());
                }

                textoModal.append("</html>");

                JOptionPane.showMessageDialog(this, textoModal.toString(), "Vaga " + this.vaga, JOptionPane.PLAIN_MESSAGE);

            });

        }

        @Override
        public void notificar() {
            this.configurar();
        }

    }

}
