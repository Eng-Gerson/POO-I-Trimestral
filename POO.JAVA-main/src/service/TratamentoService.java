package service;

import model.Tratamento;
import repository.TratamentoRepository;
import exception.EntidadeNaoEncontradaException;
import exception.TratamentoInvalidoException; // Importando a nova exceção

import java.util.List;

public class TratamentoService {

    private TratamentoRepository tratamentoRepository;

    public TratamentoService() {
        this.tratamentoRepository = new TratamentoRepository();
    }

    public void iniciarTratamento(Tratamento tratamento) {
        if (tratamento == null) {
            throw new TratamentoInvalidoException("Os dados do tratamento não foram fornecidos.");
        }

        if (tratamento.getIdTratamento() <= 0) {
            throw new TratamentoInvalidoException("ID de tratamento inválido. Deve ser maior que zero.");
        }
        
        if (tratamentoRepository.buscarPorId(tratamento.getIdTratamento()) != null) {
            throw new TratamentoInvalidoException("Já existe um tratamento registrado com o ID: " + tratamento.getIdTratamento());
        }

        if (tratamento.getPaciente() == null) {
            throw new TratamentoInvalidoException("Não é possível registrar um tratamento sem um paciente vinculado.");
        }

        if (tratamento.getDescricao() == null || tratamento.getDescricao().trim().isEmpty()) {
            throw new TratamentoInvalidoException("A descrição do tratamento é obrigatória.");
        }

        if (tratamento.getDataInicio() == null || tratamento.getDataInicio().trim().isEmpty()) {
            throw new TratamentoInvalidoException("A data de início do tratamento é obrigatória.");
        }

        tratamentoRepository.salvar(tratamento);
    }

    public Tratamento buscarPorId(int id) {
        if (id <= 0) {
            throw new TratamentoInvalidoException("ID inválido para busca. Deve ser maior que zero.");
        }
        
        Tratamento tratamento = tratamentoRepository.buscarPorId(id);
        if (tratamento == null) {
            throw new EntidadeNaoEncontradaException("Registro de tratamento ID " + id + " não encontrado.");
        }
        return tratamento;
    }

    public List<Tratamento> listarHistoricoTratamentos() {
        return tratamentoRepository.buscarTodos();
    }

    public void encerraTratamento(int id, String dataFim) {
        Tratamento tratamento = this.buscarPorId(id);

        if (dataFim == null || dataFim.trim().isEmpty()) {
            throw new TratamentoInvalidoException("A data de término deve ser informada para concluir o tratamento.");
        }

        tratamento.setDataFim(dataFim);
        tratamentoRepository.atualizar(tratamento);
    }
}