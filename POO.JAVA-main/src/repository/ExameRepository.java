package repository;

import model.Exame;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExameRepository {

    private final String CAMINHO_FICHEIRO = "exames.dat";
    private List<Exame> listaExames;

    public ExameRepository() {
        this.listaExames = carregarDoFicheiro();
    }

    public void salvar(Exame exame) {
        listaExames.add(exame);
        gravarNoFicheiro();
    }

    public List<Exame> buscarTodos() {
        return listaExames;
    }

    public Exame buscarPorId(String id) {
        for (Exame exame : listaExames) {
            if (exame.getIdExame().equals(id)) {
                return exame;
            }
        }
        return null;
    }

    public void deletar(String id) {
        Exame exame = buscarPorId(id);
        if (exame != null) {
            listaExames.remove(exame);
            gravarNoFicheiro();
        }
    }

    public void atualizar(Exame exame) {
        for (int i = 0; i < listaExames.size(); i++) {
            if (listaExames.get(i).getIdExame().equals(exame.getIdExame())) {
                listaExames.set(i, exame);
                gravarNoFicheiro();
                return;
            }
        }
    }

    private void gravarNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_FICHEIRO))) {
            oos.writeObject(listaExames);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de exames: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Exame> carregarDoFicheiro() {
        File ficheiro = new File(CAMINHO_FICHEIRO);
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
