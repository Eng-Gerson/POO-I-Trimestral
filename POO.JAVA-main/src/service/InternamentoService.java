package service;

import model.Internamento;
import repository.InternamentoRepository;
import java.util.List;

public class InternamentoService {

    private InternamentoRepository internamentoRepository;

    // Construtor padrão
    public InternamentoService() {
        this.internamentoRepository = new InternamentoRepository();
    }

    /**
     * Registra um novo internamento no histórico do hospital.
     */
    public void registrarInternamento(Internamento internamento) {
        // 1. Validação básica de nulidade
        if (internamento == null) {
            System.out.println("Erro: Os dados do internamento não foram fornecidos.");
            return;
        }

        // 2. Regra: Todos os atributos do histórico devem ser preenchidos no cadastro
        if (internamento.getIdInternamento() <= 0) {
            System.out.println("Erro: ID de internamento inválido. Deve ser maior que zero.");
            return;
        }
        if (internamento.getPaciente() == null) {
            System.out.println("Erro: Não é possível registrar internamento sem um paciente vinculado.");
            return;
        }
        if (internamento.getQuarto() <= 0) {
            System.out.println("Erro: O número do quarto deve ser informado.");
            return;
        }
        if (internamento.getDataEntrada() == null || internamento.getDataEntrada().trim().isEmpty()) {
            System.out.println("Erro: A data de entrada é obrigatória para o histórico.");
            return;
        }

        // Salva diretamente o registro no repositório de histórico
        internamentoRepository.salvar(internamento);
        System.out.println("Histórico: Internamento do paciente " + internamento.getPaciente().getNome() + " registrado com sucesso.");
    }

    /**
     * Retorna o histórico de todos os internamentos.
     */
    public List<Internamento> listarHistorico() {
        return internamentoRepository.buscarTodos();
    }

    /**
     * Busca um registro específico de internamento pelo ID.
     */
    public Internamento buscarPorId(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido para busca.");
            return null;
        }
        return internamentoRepository.buscarPorId(id);
    }

    /**
     * Atualiza o registro histórico adicionando a data de alta (saída) do paciente.
     */
    public void registrarAlta(int id, String dataSaida) {
        // 1. Busca o registro que precisa ser atualizado
        Internamento internamento = internamentoRepository.buscarPorId(id);
        if (internamento == null) {
            System.out.println("Erro: Registro de internamento ID " + id + " não encontrado.");
            return;
        }

        // 2. Valida se a data de saída foi informada
        if (dataSaida == null || dataSaida.trim().isEmpty()) {
            System.out.println("Erro: A data de saída deve ser informada para fechar o histórico.");
            return;
        }

        // 3. Atualiza o atributo de saída no objeto
        internamento.setDataSaida(dataSaida);
        
        // 4. Salva a atualização de volta no repositório
        internamentoRepository.atualizar(internamento); 
        System.out.println("Histórico Atualizado: Alta do paciente " + internamento.getPaciente().getNome() + " registrada.");
    }
}