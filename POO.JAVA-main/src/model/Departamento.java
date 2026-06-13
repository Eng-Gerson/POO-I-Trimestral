package model;

import java.io.Serializable;
import java.util.*;

public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int contador = 1;

    private int idDepartamento;
    private String nomeDepartamento;
    private List<Profissional> profissionaisDoDepartamento;

    public Departamento(String nomeDepartamento) {
        this.idDepartamento = contador++;
        this.nomeDepartamento = nomeDepartamento;
        this.profissionaisDoDepartamento = new ArrayList<>();
    }

    // Sincroniza o contador com os dados carregados do ficheiro
    public static void sincronizarContador(List<Departamento> lista) {
        lista.stream()
             .mapToInt(Departamento::getIdDepartamento)
             .max()
             .ifPresent(max -> contador = max + 1);
    }

    public int getIdDepartamento() { return idDepartamento; }

    public String getNomeDepartamento() { return nomeDepartamento; }
    public void setNomeDepartamento(String nomeDepartamento) { this.nomeDepartamento = nomeDepartamento; }

    public List<Profissional> getProfissionaisDoDepartamento() { return profissionaisDoDepartamento; }
    public void setProfissionaisDoDepartamento(List<Profissional> profissionaisDoDepartamento) {
        this.profissionaisDoDepartamento = profissionaisDoDepartamento;
    }

    @Override
    public String toString() {
        return "Departamento [nome=" + nomeDepartamento + ", idDepartamento=" + idDepartamento
                + ", profissionais=" + profissionaisDoDepartamento + "]";
    }
}
