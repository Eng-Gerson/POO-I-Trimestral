package service;

import model.Paciente;
import repository.PacienteRepository;
import java.util.List;

public class PacienteService {

    private PacienteRepository pacienteRepository;

    // Construtor que inicializa o repositório de pacientes
    public PacienteService() {
        this.pacienteRepository = new PacienteRepository();
    }

    /**
     * Cadastra um novo paciente no sistema aplicando as regras estritas de validação.
     */
    public void cadastrarPaciente(Paciente paciente) {
        // 1. Validação básica de nulidade do objeto
        if (paciente == null) {
            System.out.println("Erro Crítico: Os dados do paciente não foram fornecidos.");
            return;
        }

        // 2. Regra: Todos os atributos devem ser obrigatoriamente preenchidos
        if (paciente.getIdPaciente() <= 0) {
            System.out.println("Erro: ID do paciente inválido. Deve ser maior que zero.");
            return;
        }
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            System.out.println("Erro: O nome do paciente é obrigatório.");
            return;
        }
        if (Character.toUpperCase(paciente.getGenero()) != 'M' && Character.toUpperCase(paciente.getGenero()) != 'F') {
        System.out.println("Erro: Gênero inválido. Insira 'M' para Masculino ou 'F' para Feminino.");
        return;
    }
        if (paciente.getPeso() <= 0) {
            System.out.println("Erro: O peso do paciente deve ser informado e maior que zero.");
            return;
        }
        if (paciente.getAltura() <= 0) {
            System.out.println("Erro: A altura do paciente deve ser informada e maior que zero.");
            return;
        }

        // 3. Regra: Idade deve ser não negativa (0 para recém-nascidos ou maior)
        if (paciente.getIdade() < 0) {
            System.out.println("Erro: A idade do paciente não pode ser negativa.");
            return;
        }

        // Se passar em todas as regras, o repositório salva o paciente
        pacienteRepository.salvar(paciente);
        System.out.println("Sucesso: Paciente " + paciente.getNome() + " cadastrado com sucesso!");
    }

    /**
     * Retorna a lista de todos os pacientes cadastrados.
     */
    public List<Paciente> listarPacientes() {
        return pacienteRepository.buscarTodos();
    }

    /**
     * Busca um paciente específico utilizando o seu ID.
     */
    public Paciente buscarPacientePorId(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido para busca de paciente.");
            return null;
        }
        return pacienteRepository.buscarPorId(id);
    }

    /**
     * Remove um paciente do sistema pelo ID.
     */
    public void removerPaciente(int id) {
        Paciente pacienteExistente = pacienteRepository.buscarPorId(id);
        
        if (pacienteExistente == null) {
            System.out.println("Erro: Não é possível remover. Paciente não encontrado no sistema.");
            return;
        }

        pacienteRepository.deletar(id);
        System.out.println("Paciente ID " + id + " removido do sistema com sucesso.");
    }
}