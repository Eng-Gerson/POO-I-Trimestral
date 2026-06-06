package service;

import model.Tratamento;
import repository.TratamentoRepository;
import java.util.List;

public class TratamentoService {

    private TratamentoRepository tratamentoRepository;

    // Construtor padrão
    public TratamentoService() {
        this.tratamentoRepository = new TratamentoRepository();
    }

    /**
     * Registra o início de um novo tratamento no histórico do paciente.
     */
    public void iniciarTratamento(Tratamento tratamento) {
        // 1. Validação básica de nulidade
        if (tratamento == null) {
            System.out.println("Erro: Os dados do tratamento não foram fornecidos.");
            return;
        }

        // 2. Regra: Todos os atributos essenciais devem ser preenchidos para o registro
        if (tratamento.getIdTratamento() <= 0) {
            System.out.println("Erro: ID de tratamento inválido. Deve ser maior que zero.");
            return;
        }
        if (tratamento.getPaciente() == null) {
            System.out.println("Erro: Não é possível registrar um tratamento sem um paciente vinculado.");
            return;
        }
        if (tratamento.getDescricao() == null || tratamento.getDescricao().trim().isEmpty()) {
            System.out.println("Erro: A descrição do tratamento (ex: medicação, dosagem, fisioterapia) é obrigatória.");
            return;
        }
        if (tratamento.getDataInicio() == null || tratamento.getDataInicio().trim().isEmpty()) {
            System.out.println("Erro: A data de início do tratamento é obrigatória.");
            return;
        }

        // Salva o registro inicial no repositório de histórico
        tratamentoRepository.salvar(tratamento);
        System.out.println("Histórico: Tratamento para o paciente " + tratamento.getPaciente().getNome() + " iniciado com sucesso.");
    }

    /**
     * Retorna o histórico de todos os tratamentos registrados.
     */
    public List<Tratamento> listarHistoricoTratamentos() {
        return tratamentoRepository.buscarTodos();
    }

    /**
     * Busca um registro de tratamento específico pelo ID.
     */
    public Tratamento buscarPorId(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido para busca.");
            return null;
        }
        return tratamentoRepository.buscarPorId(id);
    }

    /**
     * Atualiza o registro adicionando a data de término do tratamento no histórico.
     */
    public void encerrarTratamento(int id, String dataFim) {
        // 1. Busca o tratamento para garantir que ele existe
        Tratamento tratamento = tratamentoRepository.buscarPorId(id);
        if (tratamento == null) {
            System.out.println("Erro: Registro de tratamento ID " + id + " não encontrado.");
            return;
        }

        // 2. Valida se a data de término foi informada
        if (dataFim == null || dataFim.trim().isEmpty()) {
            System.out.println("Erro: A data de término deve ser informada para concluir o histórico do tratamento.");
            return;
        }

        // 3. Atualiza o atributo de término no objeto
        tratamento.setDataFim(dataFim);
        
        // 4. Salva a alteração no repositório
        tratamentoRepository.atualizar(tratamento);
        System.out.println("Histórico Atualizado: Tratamento do paciente " + tratamento.getPaciente().getNome() + " foi concluído.");
    }
}