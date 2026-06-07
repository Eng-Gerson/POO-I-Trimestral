package service;

import model.Departamento;
import repository.DepartamentoRepository;
import exception.EntidadeNaoEncontradaException;

import java.util.List;

public class DepartamentoService {

    private DepartamentoRepository departamentoRepository;

    public DepartamentoService() {
        this.departamentoRepository = new DepartamentoRepository();
    }

    public void cadastrarDepartamento(Departamento departamento) {
        if (departamento == null) {
            throw new IllegalArgumentException("Os dados do departamento não foram fornecidos.");
        }
        if (departamento.getIdDepartamento() <= 0) {
            throw new IllegalArgumentException("ID do departamento inválido. Deve ser maior que zero.");
        }
        if (departamento.getNomeDepartamento() == null || departamento.getNomeDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do departamento é obrigatório.");
        }
        if (departamentoRepository.buscarPorId(departamento.getIdDepartamento()) != null) {
            throw new IllegalArgumentException("Já existe um departamento com o ID: " + departamento.getIdDepartamento());
        }

        departamentoRepository.salvar(departamento);
        System.out.println("Sucesso: Departamento " + departamento.getNomeDepartamento() + " cadastrado com sucesso!");
    }

    public List<Departamento> listarDepartamentos() {
        return departamentoRepository.buscarTodos();
    }

    public Departamento buscarDepartamentoPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido para busca. Deve ser maior que zero.");
        }
        Departamento departamento = departamentoRepository.buscarPorId(id);
        if (departamento == null) {
            throw new EntidadeNaoEncontradaException("Departamento com ID " + id + " não encontrado.");
        }
        return departamento;
    }

    public void removerDepartamento(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido para remoção. Deve ser maior que zero.");
        }
        if (departamentoRepository.buscarPorId(id) == null) {
            throw new EntidadeNaoEncontradaException("Não é possível remover. Departamento com ID " + id + " não encontrado.");
        }
        departamentoRepository.deletar(id);
        System.out.println("Departamento ID " + id + " removido do sistema com sucesso.");
    }
}
