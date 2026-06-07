package service;

import model.Exame;
import model.Consulta;
import repository.ExameRepository;
import exception.EntidadeNaoEncontradaException;
import exception.ExameInvalidoException;

import java.util.List;

public class ExameService {

    private ExameRepository exameRepository;
    private ConsultaService consultaService;

    public ExameService(ConsultaService consultaService) {
        this.exameRepository = new ExameRepository();
        this.consultaService = consultaService;
    }

    public void solicitarExame(Exame exame) {
        if (exame == null) {
            throw new ExameInvalidoException("Os dados do exame não foram fornecidos.");
        }
        if (exame.getIdExame() == null || exame.getIdExame().trim().isEmpty()) {
            throw new ExameInvalidoException("ID do exame inválido. Não pode estar vazio.");
        }
        if (exame.getTipo() == null || exame.getTipo().trim().isEmpty()) {
            throw new ExameInvalidoException("O tipo do exame (ex: Hemograma, Raio-X) é obrigatório.");
        }
        if (exame.getPaciente() == null) {
            throw new ExameInvalidoException("Todo exame precisa estar vinculado a um paciente.");
        }
        if (exame.getConsulta() == null) {
            throw new ExameInvalidoException("Um exame só pode ser solicitado através de uma consulta médica.");
        }

        Consulta consultaCadastrada = consultaService.buscarConsultaPorId(exame.getConsulta().getIdConsulta());

        int idPacienteExame = exame.getPaciente().getIdPaciente();
        int idPacienteConsulta = consultaCadastrada.getPaciente().getIdPaciente();

        if (idPacienteExame != idPacienteConsulta) {
            throw new ExameInvalidoException(
                "ALERTA DE SEGURANÇA: O paciente do exame não corresponde ao paciente da consulta vinculada.");
        }

        exameRepository.salvar(exame);
        System.out.println("Sucesso: Exame '" + exame.getTipo() + "' solicitado com sucesso para o(a) paciente " + exame.getPaciente().getNome());

    }

    public List<Exame> listarExames() {
        return exameRepository.buscarTodos();
    }

    public Exame buscarExamePorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ExameInvalidoException("ID inválido para busca de exame.");
        }
        Exame exame = exameRepository.buscarPorId(id);
        if (exame == null) {
            throw new EntidadeNaoEncontradaException("Exame com ID " + id + " não encontrado.");
        }
        return exame;
    }

    public void removerExame(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ExameInvalidoException("ID inválido para remoção de exame.");
        }
        if (exameRepository.buscarPorId(id) == null) {
            throw new EntidadeNaoEncontradaException("Não foi possível remover. Exame com ID " + id + " não encontrado.");
        }
        exameRepository.deletar(id);
        System.out.println("Registro do exame ID " + id + " removido com sucesso.");
    }
}
