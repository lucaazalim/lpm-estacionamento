package br.pucminas.titas;

import br.pucminas.titas.entidades.Estacionamento;

import java.io.*;

public class Serialization {ß

    private static final String NOME_DO_ARQUIVO = "estacionamentos.dat";

    public static void salvar(Estacionamento[] estacionamentos) throws IOException {

        try (FileOutputStream fileOut = new FileOutputStream(NOME_DO_ARQUIVO)) {
            try(ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(estacionamentos);
            }
        }

    }

    public static Estacionamento[] carregar() throws IOException, ClassNotFoundException {

        Estacionamento[] estacionamentos;

        try (FileInputStream fileIn = new FileInputStream(NOME_DO_ARQUIVO)) {
             try(ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                estacionamentos = (Estacionamento[]) objectIn.readObject();
            }
        }

        return estacionamentos;

    }

}
