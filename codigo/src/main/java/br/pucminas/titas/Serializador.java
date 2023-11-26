package br.pucminas.titas;

import br.pucminas.titas.entidades.Estacionamento;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

/**
 * Serializador de estacionamentos.
 */
public class Serializador {

    private static final Path CAMINHO = Path.of("estacionamentos.dat");

    /**
     * Salva os estacionamentos em um arquivo.
     *
     * @param estacionamentos os estacionamentos a serem salvos
     * @throws IOException se ocorrer um erro de I/O
     */
    public static void salvar(List<Estacionamento> estacionamentos) throws IOException {

        try (FileOutputStream fileOut = new FileOutputStream(CAMINHO.toString())) {
            try (ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(estacionamentos);
            }
        }

    }

    /**
     * Carrega os estacionamentos de um arquivo.
     *
     * @param consumer o consumidor dos estacionamentos
     * @throws IOException se ocorrer um erro de I/O
     * @throws ClassNotFoundException se alguma classe salva de forma serializada não for encontrada
     */
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
