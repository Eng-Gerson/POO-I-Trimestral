package service;

import model.Consulta;
import repository.ConsultaRepository;
import exception.ConsultaInvalidaException;
import exception.EntidadeNaoEncontradaException;

import java.util.List;

public class ConsultaService {

    private ConsultaRepository consultaRepository;

    public ConsultaService() {
        this.consultaRepository = new ConsultaRepository();
        // Sincroniza o contador com os dados já guardados no ficheiro
        Consulta.sincronizarContador(consultaRepository.buscarTodos());
    }

    public void agendarConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new ConsultaInvalidaException("Os dados da consulta não foram fornecidos.");
        }
        if (consulta.getPaciente() == null) {
            throw new ConsultaInvalidaException("Não é possível agendar uma consulta sem um paciente vinculado.");
        }
        if (consulta.getProfissional() == null) {
            throw new ConsultaInvalidaException("Não é possível agendar uma consulta sem um profissional de saúde vinculado.");
        }
        if (consulta.getData() == null || consulta.getData().trim().isEmpty()) {
            throw new ConsultaInvalidaException("A data da consulta é obrigatória.");
        }
        if (consulta.getHoras() == null || consulta.getHoras().trim().isEmpty()) {
            throw new ConsultaInvalidaException("A hora da consulta é obrigatória.");
        }

        consultaRepository.salvar(consulta);
        System.out.println("Consulta ID " + consulta.getIdConsulta() + " agendada com sucesso para o paciente " + consulta.getPaciente().getNome());
    }

    public List<Consulta> listarConsultas() {
        return consultaRepository.buscarTodos();
    }

    public Consulta buscarConsultaPorId(int id) {
        if (id <= 0) {
            throw new ConsultaInvalidaException("ID inválido para busca. Deve ser maior que zero.");
        }
        Consulta consulta = consultaRepository.buscarPorId(id);
        if (consulta == null) {
            throw new EntidadeNaoEncontradaException("Consulta com ID " + id + " não encontrada.");
        }
        return consulta;
    }

    public void cancelarConsulta(int id) {
        if (id <= 0) {
            throw new ConsultaInvalidaException("ID inválido para cancelamento. Deve ser maior que zero.");
        }
        if (consultaRepository.buscarPorId(id) == null) {
            throw new EntidadeNaoEncontradaException("Não é possível cancelar. Consulta com ID " + id + " não encontrada.");
        }
        consultaRepository.deletar(id);
        System.out.println("Consulta ID " + id + " cancelada com sucesso.");
    }
}
