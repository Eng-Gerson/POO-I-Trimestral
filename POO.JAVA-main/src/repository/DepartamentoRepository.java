package repository;

import model.Departamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoRepository {

    private static final String FILE_NAME = "departamentos.dat";
    private List<Departamento> listaDepartamentos;

    public DepartamentoRepository() {
        this.listaDepartamentos = carregarDados();
    }

    public void salvar(Departamento departamento) {
        listaDepartamentos.add(departamento);
        gravarDados();
        System.out.println("Departamento salvo com sucesso no ficheiro!");
    }

    public List<Departamento> buscarTodos() {
        return listaDepartamentos;
    }

    public Departamento buscarPorId(int id) {
        for (Departamento d : listaDepartamentos) {
            if (d.getIdDepartamento() == id) {
                return d;
            }
        }
        return null;
    }

    public void atualizar(Departamento departamentoAtualizado) {
        boolean atualizado = false;
        for (int i = 0; i < listaDepartamentos.size(); i++) {
            Departamento d = listaDepartamentos.get(i);
            if (d.getIdDepartamento() == departamentoAtualizado.getIdDepartamento()) {
                listaDepartamentos.set(i, departamentoAtualizado);
                atualizado = true;
                break;
            }
        }

        if (atualizado) {
            gravarDados();
            System.out.println("Departamento atualizado com sucesso no ficheiro!");
        } else {
            System.out.println("Departamento não encontrado para atualização.");
        }
    }

    public void deletar(int id) {
        Departamento departamento = buscarPorId(id);
        if (departamento != null) {
            listaDepartamentos.remove(departamento);
            gravarDados();
            System.out.println("Departamento removido com sucesso do ficheiro!");
        } else {
            System.out.println("Departamento não encontrado para remoção.");
        }
    }

    private void gravarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listaDepartamentos);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de objetos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Departamento> carregarDados() {
        File arquivo = new File(FILE_NAME);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Departamento>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler ficheiro de objetos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}