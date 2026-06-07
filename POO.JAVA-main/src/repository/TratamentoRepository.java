package repository;

import model.Tratamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TratamentoRepository {

    private final String CAMINHO_FICHEIRO = "tratamentos.dat";
    private List<Tratamento> listaTratamentos;

    public TratamentoRepository() {
        this.listaTratamentos = carregarDoFicheiro();
    }

    public void salvar(Tratamento tratamento) {
        listaTratamentos.add(tratamento);
        gravarNoFicheiro();
    }

    public List<Tratamento> buscarTodos() {
        return listaTratamentos;
    }

    public Tratamento buscarPorId(int id) {
        for (Tratamento tratamento : listaTratamentos) {
            if (tratamento.getIdTratamento() == id) {
                return tratamento;
            }
        }
        return null;
    }

    public void deletar(int id) {
        Tratamento tratamento = buscarPorId(id);
        if (tratamento != null) {
            listaTratamentos.remove(tratamento);
            gravarNoFicheiro();
        }
    }

    public void atualizar(Tratamento tratamento) {
        for (int i = 0; i < listaTratamentos.size(); i++) {
            if (listaTratamentos.get(i).getIdTratamento() == tratamento.getIdTratamento()) {
                listaTratamentos.set(i, tratamento);
                gravarNoFicheiro();
                return;
            }
        }
    }

    private void gravarNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_FICHEIRO))) {
            oos.writeObject(listaTratamentos);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de tratamentos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Tratamento> carregarDoFicheiro() {
        File ficheiro = new File(CAMINHO_FICHEIRO);
        if (!ficheiro.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (List<Tratamento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: Não foi possível ler o ficheiro de tratamentos. Criando lista vazia.");
            return new ArrayList<>();
        }
    }
}
