package repository;

import model.Exame;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExameRepository {

    // Caminho do ficheiro binário na raiz do projeto
    private final String CAMINHO_FICHEIRO = "exames.dat";
    private List<Exame> listaExames;

    // Construtor
    public ExameRepository() {
        // Carrega o histórico de exames assim que o repositório é iniciado
        this.listaExames = carregarDoFicheiro();
    }

    /**
     * Guarda um novo exame na lista e atualiza o ficheiro em disco.
     */
    public void salvar(Exame exame) {
        listaExames.add(exame);
        gravarNoFicheiro();
    }

    /**
     * Retorna a lista com todos os exames do histórico.
     */
    public List<Exame> buscarTodos() {
        return listaExames;
    }

    /**
     * Procura por um exame específico com base no ID.
     */
    public Exame buscarPorId(String id) {
        for (Exame exame : listaExames) {
            if (exame.getIdExame().equals(id)) {
                return exame;
            }
        }
        return null;
    }

    /**
     * Remove um exame do histórico e atualiza o ficheiro.
     */
    public void deletar(String id) {
        Exame exame = buscarPorId(id);
        if (exame != null) {
            listaExames.remove(exame);
            gravarNoFicheiro();
        }
    }

    /**
     * Atualiza os dados de um exame existente (ex: adicionar laudo ou resultado).
     */
    public void atualizar(Exame exame) {
        for (int i = 0; i < listaExames.size(); i++) {
            if (listaExames.get(i).getIdExame().equals(exame.getIdExame())) {
                listaExames.set(i, exame);
                gravarNoFicheiro();
                return;
            }
        }
    }

    // =========================================================================
    // MÉTODOS AUXILIARES PARA PERSISTÊNCIA EM DISCO
    // =========================================================================

    /**
     * Serializa a lista de exames e grava no ficheiro 'exames.dat'.
     */
    private void gravarNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_FICHEIRO))) {
            oos.writeObject(listaExames);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de exames: " + e.getMessage());
        }
    }

    /**
     * Desserializa o ficheiro e reconstrói a lista em memória RAM.
     */
    @SuppressWarnings("unchecked")
    private List<Exame> carregarDoFicheiro() {
        File ficheiro = new File(CAMINHO_FICHEIRO);

        // Se o ficheiro não existe, inicializa uma nova lista vazia
        if (!ficheiro.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (List<Exame>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: Não foi possível ler o ficheiro de exames. Criando lista vazia.");
            return new ArrayList<>();
        }
    }
}