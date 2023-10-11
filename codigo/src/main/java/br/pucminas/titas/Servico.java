package br.pucminas.titas;

public enum Servico {
   MANOBRISTA(5d, "Manobrista", 0), LAVAGEM(20d, "Lavagem", 1), POLIMENTO(45d, "Polimento", 2)

   private String nome;
   private double preco;
   private int horaMinimas;

   Servico(double preco, String nome, int horaMinimas) {
        this.preco = preco;
        this.nome = nome;
        this.horaMinimas = horaMinimas;
   }

   public double getPreco() {
        return preco;
   }

   public String getNome() {
        return nome;
   }

   public int getHoraMinimas() {
        return horaMinimas;
   }
}
