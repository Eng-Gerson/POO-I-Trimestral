package service;

import model.Departamento;
import repository.DepartamentoRepository;
import java.util.List;

public class DepartamentoService {

    private DepartamentoRepository departamentoRepository;

    public DepartamentoService() {
        this.departamentoRepository = new DepartamentoRepository();
    }

    /**
     * Cadastra um novo departamento no sistema aplicando as regras de validação.
     */
    public void cadastrarDepartamento(Departamento departamento) {
        if (departamento == null) {
            System.out.println("Erro Crítico: Os dados do departamento não foram fornecidos.");
            return;
        }

        if (departamento.getIdDepartamento() <= 0) {
            System.out.println("Erro: ID do departamento inválido. Deve ser maior que zero.");
            return;
        }
        
        if (departamento.getNomeDepartamento() == null || departamento.getNomeDepartamento().trim().isEmpty()) {
            System.out.println("Erro: O nome do departamento é obrigatório (ex: Urgência, Pediatria).");
            return;
        }

        departamentoRepository.salvar(departamento);
        System.out.println("Sucesso: Departamento de '" + departamento.getNomeDepartamento() + "' cadastrado com sucesso!");
    }

    /**
     * Retorna a lista de todos os departamentos cadastrados.
     */
    public List<Departamento> listarDepartamentos() {
        return departamentoRepository.buscarTodos();
    }

    /**
     * Busca um departamento específico utilizando o seu ID.
     */
    public Departamento buscarDepartamentoPorId(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido para busca de departamento.");
            return null;
        }
        return departamentoRepository.buscarPorId(id);
    }

    /**
     * Remove um departamento do sistema pelo ID.
     */
    public void removerDepartamento(int id) {
        Departamento departamentoExistente = departamentoRepository.buscarPorId(id);
        
        if (departamentoExistente == null) {
            System.out.println("Erro: Não é possível remover. Departamento não encontrado no sistema.");
            return;
        }

        departamentoRepository.deletar(id);
        System.out.println("Departamento ID " + id + " removido do sistema com sucesso.");
    }
}