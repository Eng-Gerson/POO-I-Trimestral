package model;

import java.io.Serializable;
import java.util.*;

public class Departamento implements Serializable {

    private String nomeDepartamento;
    private int idDepartamento;
    private List<Profissional> profissionaisDoDepartamento;

    public Departamento(String nomeDepartamento, int idDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
        this.idDepartamento = idDepartamento;
        this.profissionaisDoDepartamento = new ArrayList<>();
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }
    public int getIdDepartamento() {
        return idDepartamento;
    }
    public List<Profissional> getProfissionaisDoDepartamento() {
        return profissionaisDoDepartamento;
    }
    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }
    public void setProfissionaisDoDepartamento(List<Profissional> profissionaisDoDepartamento) {
        this.profissionaisDoDepartamento = profissionaisDoDepartamento;
    }
    

    @Override
    public String toString() {
        return "Departamento [nome=" + nomeDepartamento + ", idDepartamento=" + idDepartamento + ", profissionais=" + profissionaisDoDepartamento + "]";
    }

}
