package repository;

import model.Consulta;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    // Caminho do ficheiro onde os dados serão guardados
    private final String CAMINHO_FICHEIRO = "consultas.dat";
    private List<Consulta> listaConsultas;

    // Construtor
    public ConsultaRepository() {
        // Ao instanciar o repositório, ele tenta carregar os dados existentes do ficheiro
        this.listaConsultas = carregarDoFicheiro();
    }

    /**
     * Adiciona uma nova consulta à lista e grava imediatamente no ficheiro.
     */
    public void salvar(Consulta consulta) {
        listaConsultas.add(consulta);
        gravarNoFicheiro();
    }

    /**
     * Retorna todas as consultas registradas.
     */
    public List<Consulta> buscarTodos() {
        return listaConsultas;
    }

    /**
     * Busca uma consulta específica pelo ID.
     */
    public Consulta buscarPorId(int id) {
        for (Consulta consulta : listaConsultas) {
            if (consulta.getIdConsulta() == id) {
                return consulta;
            }
        }
        return null; 
    }

    /**
     * Remove uma consulta da lista e atualiza o ficheiro.
     */
    public void deletar(int id) {
        Consulta consulta = buscarPorId(id);
        if (consulta != null) {
            listaConsultas.remove(consulta);
            gravarNoFicheiro(); // Atualiza o ficheiro sem a consulta removida
        }
    }

    /**
     * Atualiza uma consulta existente e grava no ficheiro.
     */
    public void atualizar(Consulta consulta) {
        for (int i = 0; i < listaConsultas.size(); i++) {
            if (listaConsultas.get(i).getIdConsulta() == consulta.getIdConsulta()) {
                listaConsultas.set(i, consulta);
                gravarNoFicheiro();
                return;
            }
        }
    }

    // =========================================================================
    // MÉTODOS DE MANIPULAÇÃO DE FICHEIROS (MÉTODO BEM PUXADO)
    // =========================================================================

    /**
     * Grava a lista completa de consultas dentro do ficheiro de objetos.
     */
    private void gravarNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_FICHEIRO))) {
            oos.writeObject(listaConsultas);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de consultas: " + e.getMessage());
        }
    }

    /**
     * Lê o ficheiro de objetos e reconstrói a lista. 
     * Se o ficheiro não existir, cria uma lista vazia.
     */
    @SuppressWarnings("unchecked")
    private List<Consulta> carregarDoFicheiro() {
        File ficheiro = new File(CAMINHO_FICHEIRO);
        
        // Se o ficheiro ainda não existe (primeira vez a rodar o app), retorna lista vazia
        if (!ficheiro.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (List<Consulta>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: Não foi possível ler o ficheiro de consultas, criando uma nova lista.");
            return new ArrayList<>();
        }
    }
}