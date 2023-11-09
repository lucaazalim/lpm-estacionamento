package br.pucminas.titas.app;

import br.pucminas.titas.entidades.Estacionamento;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Serialization {

    private static final Path CAMINHO = Path.of("estacionamentos.dat");

    public static void salvar(List<Estacionamento> estacionamentos) throws IOException {

        try (FileOutputStream fileOut = new FileOutputStream(CAMINHO.toString())) {
            try (ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(estacionamentos);
            }
        }

    }

    public static void carregar(Consumer<Estacionamento> consumer) throws IOException, ClassNotFoundException {

        if (Files.notExists(CAMINHO)) {
            return;
        }

        List<Estacionamento> estacionamentos;

        try (FileInputStream fileIn = new FileInputStream(CAMINHO.toString())) {
            try (ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                estacionamentos = (List<Estacionamento>) objectIn.readObject();
            }
        }

        estacionamentos.forEach(consumer);

    }

}
