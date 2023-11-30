package br.pucminas.titas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Test {

    public static void main(String[] args) {
        LocalDate.parse("31/02/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.STRICT));
    }

}
