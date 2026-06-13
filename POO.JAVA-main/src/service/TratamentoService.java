package service;

import model.Tratamento;
import repository.TratamentoRepository;
import exception.EntidadeNaoEncontradaException;
import exception.TratamentoInvalidoException;
import validation.ValidadorData;

import java.util.List;

public class TratamentoService {

    private TratamentoRepository tratamentoRepository;
    private ValidadorData validadorData;

    public TratamentoService() {
        this.tratamentoRepository = new TratamentoRepository();
        this.validadorData = new ValidadorData();
        // Sincroniza o contador com os dados já guardados no ficheiro
        Tratamento.sincronizarContador(tratamentoRepository.buscarTodos());
    }

    public void iniciarTratamento(Tratamento tratamento) {
        if (tratamento == null) {
            throw new TratamentoInvalidoException("Os dados do tratamento não foram fornecidos.");
        }
        if (tratamento.getPaciente() == null) {
            throw new TratamentoInvalidoException("Não é possível registar um tratamento sem um paciente vinculado.");
        }
        if (tratamento.getProfissionais() == null || tratamento.getProfissionais().isEmpty()) {
            throw new TratamentoInvalidoException("Um tratamento deve ter pelo menos um profissional vinculado.");
        }
        if (tratamento.getDescricao() == null || tratamento.getDescricao().trim().isEmpty()) {
            throw new TratamentoInvalidoException("A descrição do tratamento é obrigatória.");
        }
        if (tratamento.getDataInicio() == null || tratamento.getDataInicio().trim().isEmpty()) {
            throw new TratamentoInvalidoException("A data de início do tratamento é obrigatória.");
        }
        if (!validadorData.validar(tratamento.getDataInicio())) {
            throw new TratamentoInvalidoException("Data de início inválida. Use o formato dd/MM/yyyy.");
        }
        if (tratamento.getDataFim() != null && !tratamento.getDataFim().trim().isEmpty()) {
            if (!validadorData.validar(tratamento.getDataFim())) {
                throw new TratamentoInvalidoException("Data de término inválida. Use o formato dd/MM/yyyy.");
            }
            if (!validadorData.validarIntervaloData(tratamento.getDataInicio(), tratamento.getDataFim())) {
                throw new TratamentoInvalidoException("A data de término deve ser igual ou depois da data de início.");
            }
        }

        tratamentoRepository.salvar(tratamento);
        System.out.println("Sucesso: Tratamento registado com ID " + tratamento.getIdTratamento() + "!");
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
        if (!validadorData.validar(dataFim)) {
            throw new TratamentoInvalidoException("Data de término inválida. Use o formato dd/MM/yyyy.");
        }
        if (!validadorData.validarIntervaloData(tratamento.getDataInicio(), dataFim)) {
            throw new TratamentoInvalidoException("A data de término deve ser igual ou depois da data de início.");
        }

        tratamento.setDataFim(dataFim);
        tratamentoRepository.atualizar(tratamento);
        System.out.println("Sucesso: Tratamento " + id + " encerrado com sucesso!");
    }

    public void concluirTratamento(int id) {
        Tratamento tratamento = this.buscarPorId(id);
        tratamento.concluir();
        tratamentoRepository.atualizar(tratamento);
        System.out.println("Sucesso: Tratamento " + id + " marcado como concluído!");
    }
}
