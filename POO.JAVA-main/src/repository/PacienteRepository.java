package repository;

import model.Paciente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    // Nome do ficheiro binário onde os dados dos pacientes serão armazenados
    private static final String FILE_NAME = "pacientes.dat";
    private List<Paciente> listaPacientes;

    public PacienteRepository() {
        // Inicializa a lista carregando o que já existe no ficheiro
        this.listaPacientes = carregarDados();
    }

    /**
     * Guarda um novo paciente e atualiza o ficheiro de objetos.
     */
    public void salvar(Paciente paciente) {
        listaPacientes.add(paciente);
        gravarDados();
        System.out.println("Paciente salvo com sucesso no ficheiro!");
    }

    /**
     * Retorna a lista atual de pacientes na memória.
     */
    public List<Paciente> buscarTodos() {
        return listaPacientes;
    }

    /**
     * Procura um paciente pelo ID.
     */
    public Paciente buscarPorId(int id) {
        for (Paciente p : listaPacientes) {
            if (p.getIdPaciente() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um paciente existente e reescreve o ficheiro.
     */
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

    /**
     * Remove um paciente e atualiza o ficheiro de objetos.
     */
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

    // ==========================================
    // MÉTODOS AUXILIARES DE LEITURA E ESCRITA
    // ==========================================

    /**
     * Grava a lista completa de pacientes no ficheiro de objetos.
     */
    private void gravarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listaPacientes);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de objetos: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista do ficheiro de objetos. Se o ficheiro não existir, cria uma lista vazia.
     */
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