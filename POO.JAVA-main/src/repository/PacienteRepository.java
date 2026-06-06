package repository;

import model.Paciente; // Importe a respectiva classe do pacote model
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {
    // Simulação de banco de dados em memória
    private List<Paciente> listaPacientes = new ArrayList<>();

    public void salvar(Paciente paciente) {
        listaPacientes.add(paciente);
        System.out.println("Paciente salvo com sucesso!");
    }

    public List<Paciente> buscarTodos() {
        return listaPacientes;
    }

    // Supondo que a classe Paciente tenha o método getId()
    public Paciente buscarPorId(int id) {
        for (Paciente p : listaPacientes) {
            if (p.getIdPaciente() == id) {
                return p;
            }
        }
        return null; // Retorna null se não encontrar
    }

    public void deletar(int id) {
        Paciente paciente = buscarPorId(id);
        if (paciente != null) {
            listaPacientes.remove(paciente);
            System.out.println("Paciente removido com sucesso!");
        } else {
            System.out.println("Paciente não encontrado para remoção.");
        }
    }
    
    // Você pode adicionar o método atualizar se a classe model permitir setters
}