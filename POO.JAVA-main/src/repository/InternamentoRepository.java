package repository;

import model.Internamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InternamentoRepository {

    private final String CAMINHO_FICHEIRO = "internamentos.dat";
    private List<Internamento> listaInternamentos;

    public InternamentoRepository() {
        this.listaInternamentos = carregarDoFicheiro();
    }

    public void salvar(Internamento internamento) {
        listaInternamentos.add(internamento);
        gravarNoFicheiro();
    }

    public List<Internamento> buscarTodos() {
        return listaInternamentos;
    }

    public Internamento buscarPorId(int id) {
        for (Internamento internamento : listaInternamentos) {
            if (internamento.getIdInternamento() == id) {
                return internamento;
            }
        }
        return null;
    }

    public void deletar(int id) {
        Internamento internamento = buscarPorId(id);
        if (internamento != null) {
            listaInternamentos.remove(internamento);
            gravarNoFicheiro();
        }
    }

    public void atualizar(Internamento internamento) {
        for (int i = 0; i < listaInternamentos.size(); i++) {
            if (listaInternamentos.get(i).getIdInternamento() == internamento.getIdInternamento()) {
                listaInternamentos.set(i, internamento);
                gravarNoFicheiro();
                return;
            }
        }
    }

    private void gravarNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_FICHEIRO))) {
            oos.writeObject(listaInternamentos);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de internamentos: " + e.getMessage());
        }
    }

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
