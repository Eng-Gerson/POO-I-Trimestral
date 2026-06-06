package service;

import model.Consulta;
import repository.ConsultaRepository;
import java.util.List;

public class ConsultaService {

    private ConsultaRepository consultaRepository;

    // Construtor que inicializa o repositório de consultas
    public ConsultaService() {
        this.consultaRepository = new ConsultaRepository();
    }

    /**
     * Regra de Negócio para Agendar uma Consulta.
     * Garante que nenhum campo lógico fique vazio e que os envolvidos existam.
     */
    public void agendarConsulta(Consulta consulta) {
        // 1. Validação básica de nulidade
        if (consulta == null) {
            System.out.println("Erro: Os dados da consulta não foram fornecidos.");
            return;
        }

        // 2. Regra: Atributos obrigatórios de identificação
        if (consulta.getIdConsulta() <= 0) {
            System.out.println("Erro: ID da consulta deve ser maior que zero.");
            return;
        }

        // 3. Regra: Uma consulta precisa obrigatoriamente de um Paciente cadastrado
        if (consulta.getPaciente() == null) {
            System.out.println("Erro: Não é possível agendar uma consulta sem um paciente vinculado.");
            return;
        }

        // 4. Regra: Uma consulta precisa obrigatoriamente de um Profissional (Médico/Enfermeiro)
        if (consulta.getProfissional() == null) {
            System.out.println("Erro: Não é possível agendar uma consulta sem um profissional de saúde vinculado.");
            return;
        }

        // 5. Regra: A data/hora da consulta não pode estar vazia
        if (consulta.getData() == null || consulta.getData().trim().isEmpty()) {
            System.out.println("Erro: A data e hora da consulta devem ser especificadas.");
            return;
        }

        // Se passar por todas as validações, salva no repositório
        consultaRepository.salvar(consulta);
        System.out.println("Consulta ID " + consulta.getIdConsulta() + " agendada com sucesso para o paciente " + consulta.getPaciente().getNome());
    }

    /**
     * Retorna a lista de todas as consultas agendadas.
     */
    public List<Consulta> listarConsultas() {
        return consultaRepository.buscarTodos();
    }

    /**
     * Busca uma consulta específica utilizando o ID.
     */
    public Consulta buscarConsultaPorId(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido para busca de consulta.");
            return null;
        }
        return consultaRepository.buscarPorId(id);
    }

    /**
     * Regra de Negócio para Cancelar uma Consulta.
     * Verifica se a consulta realmente existe antes de tentar removê-la.
     */
    public void cancelarConsulta(int id) {
        Consulta consultaExistente = consultaRepository.buscarPorId(id);

        if (consultaExistente == null) {
            System.out.println("Erro: Não foi possível cancelar. Consulta não encontrada no sistema.");
            return;
        }

        // Remove a consulta do repositório
        consultaRepository.deletar(id);
        System.out.println("Consulta ID " + id + " cancelada com sucesso.");
    }
}