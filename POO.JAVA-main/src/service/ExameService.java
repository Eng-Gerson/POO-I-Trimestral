package service;

import model.Exame;
import model.Consulta;
import repository.ExameRepository;
import java.util.List;

public class ExameService {

    private ExameRepository exameRepository;
    private ConsultaService consultaService; // Necessário para cruzar dados e aplicar regras avançadas

    /**
     * Construtor que recebe ou inicializa as dependências.
     * Passar o ConsultaService permite validar se a consulta do exame existe.
     */
    public ExameService(ConsultaService consultaService) {
        this.exameRepository = new ExameRepository();
        this.consultaService = consultaService;
    }

    /**
     * Solicita um exame aplicando regras estritas de consistência hospitalar.
     */
    public void solicitarExame(Exame exame) {
        // 1. Validação básica de nulidade do objeto
        if (exame == null) {
            System.out.println("Erro Crítico: Os dados do exame não foram fornecidos.");
            return;
        }

        // 2. Validação dos atributos obrigatórios do Exame
        if (exame.getIdExame() == null || exame.getIdExame().trim().isEmpty()) {
            System.out.println("Erro: ID do exame inválido. Deve ser maior que zero.");
            return;
        }
        if (exame.getTipo() == null || exame.getTipo().trim().isEmpty()) {
            System.out.println("Erro: O nome/tipo do exame (ex: Hemograma, Raio-X) é obrigatório.");
            return;
        }
        if (exame.getPaciente() == null) {
            System.out.println("Erro: Todo exame precisa estar vinculado a um Paciente.");
            return;
        }

        // 3. REGRA AVANÇADA: O exame precisa nascer de uma Consulta
        if (exame.getConsulta() == null) {
            System.out.println("Erro de Protocolo: Um exame só pode ser solicitado através de uma consulta médica.");
            return;
        }

        // 4. REGRA AVANÇADA: Verificar se a consulta vinculada realmente existe no sistema
        Consulta consultaCadastrada = consultaService.buscarConsultaPorId(exame.getConsulta().getIdConsulta());
        if (consultaCadastrada == null) {
            System.out.println("Erro de Consistência: A consulta informada (ID " + exame.getConsulta().getIdConsulta() + ") não existe no sistema.");
            return;
        }

        // 5. REGRA CRÍTICA: O paciente do exame deve ser o mesmo paciente da consulta
        int idPacienteExame = exame.getPaciente().getIdPaciente();
        int idPacienteConsulta = consultaCadastrada.getPaciente().getIdPaciente();
        
        if (idPacienteExame != idPacienteConsulta) {
            System.out.println("ALERTA DE SEGURANÇA: O paciente solicitado para o exame não corresponde ao paciente da consulta médica vinculada!");
            return;
        }

        // Se passou em toda a linha de defesa, o exame é persistido
        exameRepository.salvar(exame);
        System.out.println("Sucesso: Exame '" + exame.getTipo() + "' solicitado com sucesso para o(a) paciente " + exame.getPaciente().getNome());
    }

    /**
     * Retorna a lista de todos os exames do hospital.
     */
    public List<Exame> listarExames() {
        return exameRepository.buscarTodos();
    }

    /**
     * Busca um exame específico pelo ID.
     */
    public Exame buscarExamePorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Erro: ID inválido para busca de exame.");
            return null;
        }
        return exameRepository.buscarPorId(id);
    }

    /**
     * Cancela/Remove a solicitação de um exame.
     */
    public void removerExame(String id) {
        Exame exameExistente = exameRepository.buscarPorId(id);

        if (exameExistente == null) {
            System.out.println("Erro: Não foi possível remover. Registro de exame não encontrado.");
            return;
        }

        exameRepository.deletar(id);
        System.out.println("Registro do exame ID " + id + " removido com sucesso.");
    }
}