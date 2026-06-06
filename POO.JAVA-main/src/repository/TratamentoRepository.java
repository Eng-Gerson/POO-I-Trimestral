package repository;

import model.Tratamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TratamentoRepository {

    // Caminho do ficheiro binário na raiz do projeto
    private final String CAMINHO_FICHEIRO = "tratamentos.dat";
    private List<Tratamento> listaTratamentos;

    // Construtor
    public TratamentoRepository() {
        // Tenta recuperar o histórico de tratamentos do ficheiro assim que o repositório é instanciado
        this.listaTratamentos = carregarDoFicheiro();
    }

    /**
     * Insere um novo tratamento na lista e persiste a alteração no ficheiro.
     */
    public void salvar(Tratamento tratamento) {
        listaTratamentos.add(tratamento);
        gravarNoFicheiro();
    }

    /**
     * Retorna todos os históricos de tratamentos gravados.
     */
    public List<Tratamento> buscarTodos() {
        return listaTratamentos;
    }

    /**
     * Procura por um tratamento específico usando o ID de registro.
     */
    public Tratamento buscarPorId(int id) {
        for (Tratamento tratamento : listaTratamentos) {
            if (tratamento.getIdTratamento() == id) {
                return tratamento;
            }
        }
        return null;
    }

    /**
     * Remove um tratamento da lista e atualiza o ficheiro em disco.
     */
    public void deletar(int id) {
        Tratamento tratamento = buscarPorId(id);
        if (tratamento != null) {
            listaTratamentos.remove(tratamento);
            gravarNoFicheiro();
        }
    }

    /**
     * Atualiza um tratamento existente.
     * Essencial para o TratamentoService gravar a data de conclusão (dataFim) do protocolo.
     */
    public void atualizar(Tratamento tratamento) {
        for (int i = 0; i < listaTratamentos.size(); i++) {
            if (listaTratamentos.get(i).getIdTratamento() == tratamento.getIdTratamento()) {
                listaTratamentos.set(i, tratamento);
                gravarNoFicheiro(); // Salva a data de fim ou alterações de evolução clínica no disco
                return;
            }
        }
    }

    // =========================================================================
    // MÉTODOS DE SUPORTE PARA GRAVAÇÃO E LEITURA DE FICHEIROS BINÁRIOS
    // =========================================================================

    /**
     * Grava a lista atualizada de tratamentos no ficheiro.
     */
    private void gravarNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_FICHEIRO))) {
            oos.writeObject(listaTratamentos);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de tratamentos: " + e.getMessage());
        }
    }

    /**
     * Lê o ficheiro e recupera a lista de objetos.
     * Retorna uma lista vazia se o ficheiro não existir.
     */
    @SuppressWarnings("unchecked")
    private List<Tratamento> carregarDoFicheiro() {
        File ficheiro = new File(CAMINHO_FICHEIRO);

        if (!ficheiro.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (List<Tratamento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: Não foi possível ler o ficheiro de tratamentos. Criando lista vazia.");
            return new ArrayList<>();
        }
    }
}