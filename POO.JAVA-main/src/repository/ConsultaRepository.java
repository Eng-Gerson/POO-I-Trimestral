package repository;

import model.Consulta;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    private final String CAMINHO_FICHEIRO = "consultas.dat";
    private List<Consulta> listaConsultas;

    public ConsultaRepository() {
        this.listaConsultas = carregarDoFicheiro();
    }

    public void salvar(Consulta consulta) {
        listaConsultas.add(consulta);
        gravarNoFicheiro();
    }

    public List<Consulta> buscarTodos() {
        return listaConsultas;
    }

    public Consulta buscarPorId(int id) {
        for (Consulta consulta : listaConsultas) {
            if (consulta.getIdConsulta() == id) {
                return consulta;
            }
        }
        return null;
    }

    public void deletar(int id) {
        Consulta consulta = buscarPorId(id);
        if (consulta != null) {
            listaConsultas.remove(consulta);
            gravarNoFicheiro();
        }
    }

    public void atualizar(Consulta consulta) {
        for (int i = 0; i < listaConsultas.size(); i++) {
            if (listaConsultas.get(i).getIdConsulta() == consulta.getIdConsulta()) {
                listaConsultas.set(i, consulta);
                gravarNoFicheiro();
                return;
            }
        }
    }

    private void gravarNoFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_FICHEIRO))) {
            oos.writeObject(listaConsultas);
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no ficheiro de consultas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Consulta> carregarDoFicheiro() {
        File ficheiro = new File(CAMINHO_FICHEIRO);
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
