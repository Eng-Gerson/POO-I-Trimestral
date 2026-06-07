package service;

import model.Paciente;
import repository.PacienteRepository;
import exception.EntidadeNaoEncontradaException;
import exception.PacienteInvalidoException;

import java.util.List;

public class PacienteService {

    private PacienteRepository pacienteRepository;

    public PacienteService() {
        this.pacienteRepository = new PacienteRepository();
    }

    public void cadastrarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new PacienteInvalidoException("Os dados do paciente não foram fornecidos.");
        }
        if (paciente.getIdPaciente() <= 0) {
            throw new PacienteInvalidoException("ID do paciente inválido. Deve ser maior que zero.");
        }
        if (pacienteRepository.buscarPorId(paciente.getIdPaciente()) != null) {
            throw new PacienteInvalidoException("Já existe um paciente registado com o ID: " + paciente.getIdPaciente());
        }
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            throw new PacienteInvalidoException("O nome do paciente é obrigatório.");
        }
        if (Character.toUpperCase(paciente.getGenero()) != 'M' && Character.toUpperCase(paciente.getGenero()) != 'F') {
            throw new PacienteInvalidoException("Género inválido. Insira 'M' para Masculino ou 'F' para Feminino.");
        }
        if (paciente.getPeso() <= 0) {
            throw new PacienteInvalidoException("O peso do paciente deve ser maior que zero.");
        }
        if (paciente.getAltura() <= 0) {
            throw new PacienteInvalidoException("A altura do paciente deve ser maior que zero.");
        }
        if (paciente.getIdade() < 0) {
            throw new PacienteInvalidoException("A idade do paciente não pode ser negativa.");
        }

        pacienteRepository.salvar(paciente);
        System.out.println("Sucesso: Paciente " + paciente.getNome() + " cadastrado com sucesso!");
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.buscarTodos();
    }

    public Paciente buscarPacientePorId(int id) {
        if (id <= 0) {
            throw new PacienteInvalidoException("ID inválido para busca. Deve ser maior que zero.");
        }
        Paciente paciente = pacienteRepository.buscarPorId(id);
        if (paciente == null) {
            throw new EntidadeNaoEncontradaException("Paciente com ID " + id + " não encontrado.");
        }
        return paciente;
    }

    public void removerPaciente(int id) {
        if (id <= 0) {
            throw new PacienteInvalidoException("ID inválido para remoção. Deve ser maior que zero.");
        }
        if (pacienteRepository.buscarPorId(id) == null) {
            throw new EntidadeNaoEncontradaException("Não é possível remover. Paciente com ID " + id + " não encontrado.");
        }
        pacienteRepository.deletar(id);
        System.out.println("Paciente ID " + id + " removido do sistema com sucesso.");
    }
}
