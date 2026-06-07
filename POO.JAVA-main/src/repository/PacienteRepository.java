package repository;

import model.Paciente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    private static final String FILE_NAME = "pacientes.dat";
    private List<Paciente> listaPacientes;

    public PacienteRepository() {
        this.listaPacientes = carregarDados();
    }

    public void salvar(Paciente paciente) {
        listaPacientes.add(paciente);
        gravarDados();
        System.out.println("Paciente salvo com sucesso no ficheiro!");
    }

    public List<Paciente> buscarTodos() {
        return listaPacientes;
    }

    public Paciente buscarPorId(int id) {
        for (Paciente p : listaPacientes) {
            if (p.getIdPaciente() == id) {
                return p;
            }
        }
        return null;
    }

    public void atualizar(Paciente pacienteAtualizado) {
        boolean atualizado = false;
        for (int i = 0; i < listaPacientes.size(); i++) {
            Paciente p = listaPacientes.get(i);
            if (p.getIdPaciente() == pacienteAtualizado.getIdPaciente()) {
                listaPacientes.set(i, pacienteAtualizado);
                atualizado = true;
                break;
            }
        }
        if (atualizado) {
            gravarDados();
            System.out.println("Paciente atualizado com sucesso no ficheiro!");
        } else {
            System.out.println("Paciente não encontrado para atualização.");
        }
    }

    public void deletar(int id) {
        Paciente paciente = buscarPorId(id);
        if (paciente != null) {
            listaPacientes.remove(paciente);
            gravarDados();
            System.out.println("Paciente removido com sucesso do ficheiro!");
        } else {
            System.out.println("Paciente não encontrado para remoção.");
        }
    }

    private void gravarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listaPacientes);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de objetos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Paciente> carregarDados() {
        File arquivo = new File(FILE_NAME);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Paciente>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler ficheiro de objetos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
