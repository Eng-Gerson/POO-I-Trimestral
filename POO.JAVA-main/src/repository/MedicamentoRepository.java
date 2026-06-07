package repository;

import model.Medicamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepository {

    // Nome do ficheiro binário onde os dados dos medicamentos serão armazenados
    private static final String FILE_NAME = "medicamentos.dat";
    private List<Medicamento> listaMedicamentos;

    public MedicamentoRepository() {
        // Inicializa a lista carregando o que já existe no ficheiro
        this.listaMedicamentos = carregarDados();
    }

    /**
     * Guarda um novo medicamento e atualiza o ficheiro de objetos.
     */
    public void salvar(Medicamento medicamento) {
        listaMedicamentos.add(medicamento);
        gravarDados();
        System.out.println("Medicamento salvo com sucesso no ficheiro!");
    }

    /**
     * Retorna a lista atual de medicamentos na memória.
     */
    public List<Medicamento> buscarTodos() {
        return listaMedicamentos;
    }

    /**
     * Procura um medicamento pelo ID.
     */
    public Medicamento buscarPorId(int id) {
        for (Medicamento m : listaMedicamentos) {
            if (m.getIdMedicamento() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um medicamento existente e reescreve o ficheiro.
     */
    public void atualizar(Medicamento medicamentoAtualizado) {
        boolean atualizado = false;
        for (int i = 0; i < listaMedicamentos.size(); i++) {
            Medicamento m = listaMedicamentos.get(i);
            if (m.getIdMedicamento() == medicamentoAtualizado.getIdMedicamento()) {
                listaMedicamentos.set(i, medicamentoAtualizado);
                atualizado = true;
                break;
            }
        }
        
        if (atualizado) {
            gravarDados();
            System.out.println("Medicamento atualizado com sucesso no ficheiro!");
        } else {
            System.out.println("Medicamento não encontrado para atualização.");
        }
    }

    /**
     * Remove um medicamento e atualiza o ficheiro de objetos.
     */
    public void deletar(int id) {
        Medicamento medicamento = buscarPorId(id);
        if (medicamento != null) {
            listaMedicamentos.remove(medicamento);
            gravarDados();
            System.out.println("Medicamento removido com sucesso do ficheiro!");
        } else {
            System.out.println("Medicamento não encontrado para remoção.");
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE LEITURA E ESCRITA
    // ==========================================

    /**
     * Grava a lista completa de medicamentos no ficheiro de objetos.
     */
    private void gravarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listaMedicamentos);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de objetos: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista do ficheiro de objetos. Se o ficheiro não existir, cria uma lista vazia.
     */
    @SuppressWarnings("unchecked")
    private List<Medicamento> carregarDados() {
        File arquivo = new File(FILE_NAME);
        
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Medicamento>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler ficheiro de objetos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}