package service;

import model.Profissional;
import repository.ProfissionalRepository;
import java.util.List;

public class ProfissionalService {

    private ProfissionalRepository profissionalRepository;

    public ProfissionalService() {
        this.profissionalRepository = new ProfissionalRepository();
    }

    /**
     * Cadastra um novo profissional de saúde no sistema aplicando regras de validação.
     */
    public void cadastrarProfissional(Profissional profissional) {
        if (profissional == null) {
            System.out.println("Erro Crítico: Os dados do profissional não foram fornecidos.");
            return;
        }

        // Assumindo que a classe Profissional herda de Pessoa ou tem getIdProfissional()
        if (profissional.getIdProfissional() <= 0) {
            System.out.println("Erro: ID do profissional inválido. Deve ser maior que zero.");
            return;
        }
        if (profissional.getNome() == null || profissional.getNome().trim().isEmpty()) {
            System.out.println("Erro: O nome do profissional é obrigatório.");
            return;
        }

        profissionalRepository.salvar(profissional);
        System.out.println("Sucesso: Profissional " + profissional.getNome() + " cadastrado com sucesso!");
    }

    public List<Profissional> listarProfissionais() {
        return profissionalRepository.buscarTodos();
    }

    public Profissional buscarProfissionalPorId(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido para busca de profissional.");
            return null;
        }
        return profissionalRepository.buscarPorId(id);
    }

    public void removerProfissional(int id) {
        Profissional profissionalExistente = profissionalRepository.buscarPorId(id);
        
        if (profissionalExistente == null) {
            System.out.println("Erro: Não é possível remover. Profissional não encontrado no sistema.");
            return;
        }

        profissionalRepository.deletar(id);
        System.out.println("Profissional ID " + id + " removido do sistema com sucesso.");
    }
}