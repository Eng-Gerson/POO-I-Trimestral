package service;

import model.Medicamento;
import repository.MedicamentoRepository;
import exception.EntidadeNaoEncontradaException;
import exception.MedicamentoInvalidoException;

import java.util.List;

public class MedicamentoService {

    private MedicamentoRepository medicamentoRepository;

    public MedicamentoService() {
        this.medicamentoRepository = new MedicamentoRepository();
    }

    public void cadastrarMedicamento(Medicamento medicamento) {
        if (medicamento == null) {
            throw new MedicamentoInvalidoException("Os dados do medicamento não foram fornecidos.");
        }
        if (medicamento.getIdMedicamento() <= 0) {
            throw new MedicamentoInvalidoException("ID do medicamento inválido. Deve ser maior que zero.");
        }
        if (medicamentoRepository.buscarPorId(medicamento.getIdMedicamento()) != null) {
            throw new MedicamentoInvalidoException("Já existe um medicamento registado com o ID: " + medicamento.getIdMedicamento());
        }
        if (medicamento.getNome() == null || medicamento.getNome().trim().isEmpty()) {
            throw new MedicamentoInvalidoException("O nome do medicamento é obrigatório.");
        }
        if (medicamento.getDosagem() == null || medicamento.getDosagem().trim().isEmpty()) {
            throw new MedicamentoInvalidoException("A dosagem do medicamento é obrigatória.");
        }

        medicamentoRepository.salvar(medicamento);
        System.out.println("Sucesso: Medicamento '" + medicamento.getNome() + "' cadastrado com sucesso!");
    }

    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.buscarTodos();
    }

    public Medicamento buscarMedicamentoPorId(int id) {
        if (id <= 0) {
            throw new MedicamentoInvalidoException("ID inválido para busca. Deve ser maior que zero.");
        }
        Medicamento medicamento = medicamentoRepository.buscarPorId(id);
        if (medicamento == null) {
            throw new EntidadeNaoEncontradaException("Medicamento com ID " + id + " não encontrado.");
        }
        return medicamento;
    }

    public void removerMedicamento(int id) {
        if (id <= 0) {
            throw new MedicamentoInvalidoException("ID inválido para remoção. Deve ser maior que zero.");
        }
        if (medicamentoRepository.buscarPorId(id) == null) {
            throw new EntidadeNaoEncontradaException("Não é possível remover. Medicamento com ID " + id + " não encontrado.");
        }
        medicamentoRepository.deletar(id);
        System.out.println("Medicamento ID " + id + " removido do sistema com sucesso.");
    }
}
