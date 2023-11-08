package br.pucminas.titas;

import br.pucminas.titas.entidades.Estacionamento;

import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Serialization {

    private static final String NOME_DO_ARQUIVO = "estacionamentos.dat";

    public static void salvar(Collection<Estacionamento> estacionamentos) throws IOException {

        try (FileOutputStream fileOut = new FileOutputStream(NOME_DO_ARQUIVO)) {
            try(ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(estacionamentos.toArray());
            }
        }

    }

    public static Collection<Estacionamento> carregar() throws IOException, ClassNotFoundException {

        Estacionamento[] estacionamentos;

        try (FileInputStream fileIn = new FileInputStream(NOME_DO_ARQUIVO)) {
             try(ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                estacionamentos = (Estacionamento[]) objectIn.readObject();
            }
        }

        return List.of(estacionamentos);

    }

}
