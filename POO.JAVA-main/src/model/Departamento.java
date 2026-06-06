package model;

import java.io.Serializable;
import model.Profissional;
import java.util.*;

public class Departamento implements Serializable {

    private String nomeDepartamento;
    private int idDepartamento;
    private ArrayList<Profissional> profissionaisDoDepartamento;

    public Departamento(String nomeDepartamento, int idDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
        this.idDepartamento = idDepartamento;
        this.profissionaisDoDepartamento = new ArrayList<>();
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    private List<Profissional> profissionais;
    public String getNome() {
        return nome;
    }
    public int getIdDepartamento() {
        return idDepartamento;
    }
    public ArrayList<Profissional> getProfissionaisDoDepartamento() {
        return profissionaisDoDepartamento;
    }
    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }
    public void setProfissionaisDoDepartamento(ArrayList<Profissional> profissionaisDoDepartamento) {
        this.profissionaisDoDepartamento = profissionaisDoDepartamento;
    public List<Profissional> getProfissionais() {
        return profissionais;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
     public void setProfissionais(List<Profissional> profissionais) {
        this.profissionais = profissionais;
    }
    
    public Departamento(String nome, int idDepartamento) {
        this.nome = nome;
        this.idDepartamento = idDepartamento;
        this.profissionais=profissionais;
    }

    @Override
    public String toString() {
        return "Departamento [nome=" + nome + ", idDepartamento=" + idDepartamento + ", profissionais=" + profissionais + "]";
    }

}
