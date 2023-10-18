package br.pucminas.titas.enums;

public enum Servico {
    MANOBRISTA("Manobrista", 5, 0),
    LAVAGEM("Lavagem", 20, 1),
    POLIMENTO("Polimento", 45, 2);

    private String nome;
    private double preco;
    private int horaMinimas;

    Servico(String nome, double preco, int horasMinimas) {
        this.nome = nome;
        this.preco = preco;
        this.horaMinimas = horasMinimas;
    }

    public double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    public int getHorasMinimas() {
        return horaMinimas;
    }
}
