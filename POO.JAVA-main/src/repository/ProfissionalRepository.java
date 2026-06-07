package repository;

import model.Profissional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProfissionalRepository {

    private static final String FILE_NAME = "profissionais.dat";
    private List<Profissional> listaProfissionais;

    public ProfissionalRepository() {
        this.listaProfissionais = carregarDados();
    }

    public void salvar(Profissional profissional) {
        listaProfissionais.add(profissional);
        gravarDados();
        System.out.println("Profissional salvo com sucesso no ficheiro!");
    }

    public List<Profissional> buscarTodos() {
        return listaProfissionais;
    }

    public Profissional buscarPorId(int id) {
        for (Profissional p : listaProfissionais) {
            if (p.getIdProfissional() == id) {
                return p;
            }
        }
        return null;
    }

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

    private void gravarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listaProfissionais);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de objetos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Profissional> carregarDados() {
        File arquivo = new File(FILE_NAME);
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
