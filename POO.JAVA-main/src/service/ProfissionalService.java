package service;

import model.Profissional;
import repository.ProfissionalRepository;
import exception.EntidadeNaoEncontradaException;
import exception.ProfissionalInvalidoException;

import java.util.List;

public class ProfissionalService {

    private ProfissionalRepository profissionalRepository;

    public ProfissionalService() {
        this.profissionalRepository = new ProfissionalRepository();
        // Sincroniza o contador com os dados já guardados no ficheiro
        Profissional.sincronizarContador(profissionalRepository.buscarTodos());
    }

    public void cadastrarProfissional(Profissional profissional) {
        if (profissional == null) {
            throw new ProfissionalInvalidoException("Os dados do profissional não foram fornecidos.");
        }
        if (profissional.getNome() == null || profissional.getNome().trim().isEmpty()) {
            throw new ProfissionalInvalidoException("O nome do profissional é obrigatório.");
        }
        if (profissional.getNumeroCedulaProfissional() == null || profissional.getNumeroCedulaProfissional().trim().isEmpty()) {
            throw new ProfissionalInvalidoException("O número da cédula profissional é obrigatório.");
        }
        if (profissional.getDepartamento() == null) {
            throw new ProfissionalInvalidoException("O profissional deve estar associado a um departamento.");
        }

        profissionalRepository.salvar(profissional);
        System.out.println("Sucesso: Profissional " + profissional.getNome() + " cadastrado com ID " + profissional.getIdProfissional() + "!");
    }

    public List<Profissional> listarProfissionais() {
        return profissionalRepository.buscarTodos();
    }

    public Profissional buscarProfissionalPorId(int id) {
        if (id <= 0) {
            throw new ProfissionalInvalidoException("ID inválido para busca. Deve ser maior que zero.");
        }
        Profissional profissional = profissionalRepository.buscarPorId(id);
        if (profissional == null) {
            throw new EntidadeNaoEncontradaException("Profissional com ID " + id + " não encontrado.");
        }
        return profissional;
    }

    public void removerProfissional(int id) {
        if (id <= 0) {
            throw new ProfissionalInvalidoException("ID inválido para remoção. Deve ser maior que zero.");
        }
        if (profissionalRepository.buscarPorId(id) == null) {
            throw new EntidadeNaoEncontradaException("Não é possível remover. Profissional com ID " + id + " não encontrado.");
        }
        profissionalRepository.deletar(id);
        System.out.println("Profissional ID " + id + " removido do sistema com sucesso.");
    }
}
