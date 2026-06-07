package service;

import model.Medicamento;
import repository.MedicamentoRepository;
import java.util.List;

public class MedicamentoService {

    private MedicamentoRepository medicamentoRepository;

    public MedicamentoService() {
        this.medicamentoRepository = new MedicamentoRepository();
    }

    /**
     * Cadastra um novo medicamento no sistema aplicando as regras de validação.
     */
    public void cadastrarMedicamento(Medicamento medicamento) {
        if (medicamento == null) {
            System.out.println("Erro Crítico: Os dados do medicamento não foram fornecidos.");
            return;
        }

        if (medicamento.getIdMedicamento() <= 0) {
            System.out.println("Erro: ID do medicamento inválido. Deve ser maior que zero.");
            return;
        }
        
        if (medicamento.getNome() == null || medicamento.getNome().trim().isEmpty()) {
            System.out.println("Erro: O nome do medicamento é obrigatório.");
            return;
        }

        medicamentoRepository.salvar(medicamento);
        System.out.println("Sucesso: Medicamento '" + medicamento.getNome() + "' cadastrado com sucesso!");
    }

    /**
     * Retorna a lista de todos os medicamentos cadastrados.
     */
    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.buscarTodos();
    }

    /**
     * Busca um medicamento específico utilizando o seu ID.
     */
    public Medicamento buscarMedicamentoPorId(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido para busca de medicamento.");
            return null;
        }
        return medicamentoRepository.buscarPorId(id);
    }

    /**
     * Remove um medicamento do sistema pelo ID.
     */
    public void removerMedicamento(int id) {
        Medicamento medicamentoExistente = medicamentoRepository.buscarPorId(id);
        
        if (medicamentoExistente == null) {
            System.out.println("Erro: Não é possível remover. Medicamento não encontrado no sistema.");
            return;
        }

        medicamentoRepository.deletar(id);
        System.out.println("Medicamento ID " + id + " removido do sistema com sucesso.");
    }
}