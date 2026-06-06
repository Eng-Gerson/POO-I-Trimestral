package repository;

import model.Internamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InternamentoRepository {

    // Nome do ficheiro onde o histórico de internamentos será persistido
    private final String CAMINHO_FICHEIRO = "internamentos.dat";
    private List<Internamento> listaInternamentos;

    // Construtor
    public InternamentoRepository() {
        // Carrega o histórico de internamentos do ficheiro assim que o repositório inicia
        this.listaInternamentos = carregarDoFicheiro();
    }

    /**
     * Adiciona um novo registro de internamento e grava em disco.
     */
    public void salvar(Internamento internamento) {
        listaInternamentos.add(internamento);
        gravarNoFicheiro();
    }

    /**
     * Retorna a lista completa com todo o histórico de internamentos.
     */
    public List<Internamento> buscarTodos() {
        return listaInternamentos;
    }

    /**
     * Busca um registro de internamento específico pelo ID.
     */
    public Internamento buscarPorId(int id) {
        for (Internamento internamento : listaInternamentos) {
            if (internamento.getIdInternamento() == id) {
                return internamento;
            }
        }
        return null;
    }

    /**
     * Remove um registro de internamento do histórico e atualiza o ficheiro.
     */
    public void deletar(int id) {
        Internamento internamento = buscarPorId(id);
        if (internamento != null) {
            listaInternamentos.remove(internamento);
            gravarNoFicheiro();
        }
    }

    /**
     * Atualiza um registro de internamento existente.
     * Fundamental para gravar a data de alta do paciente no histórico.
     */
    public void atualizar(Internamento internamento) {
        for (int i = 0; i < listaInternamentos.size(); i++) {
            if (listaInternamentos.get(i).getIdInternamento() == internamento.getIdInternamento()) {
                listaInternamentos.set(i, internamento);
                gravarNoFicheiro(); // Salva a alteração (como a inclusão da data de alta) no disco
                return;
            }
        }
    }

    // =========================================================================
    // MÉTODOS INTERNOS PARA MANIPULAÇÃO DO FICHEIRO BINÁRIO
    // =========================================================================

    /**
     * Serializa a lista de internamentos e grava no ficheiro 'internamentos.dat'.
     */
    private void gravarNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_FICHEIRO))) {
            oos.writeObject(listaInternamentos);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de internamentos: " + e.getMessage());
        }
    }

    /**
     * Desserializa o ficheiro e recupera a lista de histórico.
     * Caso o ficheiro não exista, inicializa uma lista vazia.
     */
    @SuppressWarnings("unchecked")
    private List<Internamento> carregarDoFicheiro() {
        File ficheiro = new File(CAMINHO_FICHEIRO);

        if (!ficheiro.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (List<Internamento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: Não foi possível ler o ficheiro de internamentos. Criando lista vazia.");
            return new ArrayList<>();
        }
    }
}