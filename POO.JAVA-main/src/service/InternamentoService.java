package service;

import model.Internamento;
import repository.InternamentoRepository;
import exception.EntidadeNaoEncontradaException;
import exception.InternamentoInvalidoException;

import java.util.List;

public class InternamentoService {

    private InternamentoRepository internamentoRepository;

    public InternamentoService() {
        this.internamentoRepository = new InternamentoRepository();
        // Sincroniza o contador com os dados já guardados no ficheiro
        Internamento.sincronizarContador(internamentoRepository.buscarTodos());
    }

    public void registrarInternamento(Internamento internamento) {
        if (internamento == null) {
            throw new InternamentoInvalidoException("Os dados do internamento não foram fornecidos.");
        }
        if (internamento.getPaciente() == null) {
            throw new InternamentoInvalidoException("Não é possível registar internamento sem um paciente vinculado.");
        }
        if (internamento.getQuarto() <= 0) {
            throw new InternamentoInvalidoException("O número do quarto deve ser informado.");
        }
        if (internamento.getDataEntrada() == null || internamento.getDataEntrada().trim().isEmpty()) {
            throw new InternamentoInvalidoException("A data de entrada é obrigatória para o registo.");
        }

        internamentoRepository.salvar(internamento);
        System.out.println("Histórico: Internamento do paciente " + internamento.getPaciente().getNome()
                + " registrado com ID " + internamento.getIdInternamento() + " com sucesso.");
    }

    public List<Internamento> listarHistorico() {
        return internamentoRepository.buscarTodos();
    }

    public Internamento buscarPorId(int id) {
        if (id <= 0) {
            throw new InternamentoInvalidoException("ID inválido para busca. Deve ser maior que zero.");
        }
        Internamento internamento = internamentoRepository.buscarPorId(id);
        if (internamento == null) {
            throw new EntidadeNaoEncontradaException("Internamento com ID " + id + " não encontrado.");
        }
        return internamento;
    }

    public void registrarAlta(int id, String dataSaida) {
        Internamento internamento = this.buscarPorId(id);

        if (dataSaida == null || dataSaida.trim().isEmpty()) {
            throw new InternamentoInvalidoException("A data de saída deve ser informada para fechar o registo.");
        }

        internamento.setDataSaida(dataSaida);
        internamentoRepository.atualizar(internamento);
        System.out.println("Histórico Atualizado: Alta do paciente " + internamento.getPaciente().getNome() + " registrada.");
    }
}
