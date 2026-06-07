package repository;

import model.Profissional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProfissionalRepository {

    // Nome do ficheiro binário onde os dados serão armazenados
    private static final String FILE_NAME = "profissionais.dat";
    private List<Profissional> listaProfissionais;

    public ProfissionalRepository() {
        // Inicializa a lista carregando o que já existe no ficheiro
        this.listaProfissionais = carregarDados();
    }

    /**
     * Guarda um novo profissional e atualiza o ficheiro de objetos.
     */
    public void salvar(Profissional profissional) {
        listaProfissionais.add(profissional);
        gravarDados();
        System.out.println("Profissional salvo com sucesso no ficheiro!");
    }

    /**
     * Retorna a lista atual de profissionais na memória.
     */
    public List<Profissional> buscarTodos() {
        return listaProfissionais;
    }

    /**
     * Procura um profissional pelo ID.
     */
    public Profissional buscarPorId(int id) {
        for (Profissional p : listaProfissionais) {
            if (p.getIdProfissional() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um profissional existente e reescreve o ficheiro.
     */
    public void atualizar(Profissional profissionalAtualizado) {
        boolean atualizado = false;
        for (int i = 0; i < listaProfissionais.size(); i++) {
            Profissional p = listaProfissionais.get(i);
            if (p.getIdProfissional() == profissionalAtualizado.getIdProfissional()) {
                listaProfissionais.set(i, profissionalAtualizado);
                atualizado = true;
                break;
            }
        }
        
        if (atualizado) {
            gravarDados();
            System.out.println("Profissional atualizado com sucesso no ficheiro!");
        } else {
            System.out.println("Profissional não encontrado para atualização.");
        }
    }

    /**
     * Remove um profissional e atualiza o ficheiro de objetos.
     */
    public void deletar(int id) {
        Profissional profissional = buscarPorId(id);
        if (profissional != null) {
            listaProfissionais.remove(profissional);
            gravarDados();
            System.out.println("Profissional removido com sucesso do ficheiro!");
        } else {
            System.out.println("Profissional não encontrado para remoção.");
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE LEITURA E ESCRITA
    // ==========================================

    /**
     * Grava a lista completa de profissionais no ficheiro de objetos.
     */
    private void gravarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listaProfissionais);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de objetos: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista do ficheiro de objetos. Se o ficheiro não existir, cria uma lista vazia.
     */
    @SuppressWarnings("unchecked")
    private List<Profissional> carregarDados() {
        File arquivo = new File(FILE_NAME);
        
        // Se o ficheiro ainda não existe, retorna uma nova lista vazia
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Profissional>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler ficheiro de objetos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}