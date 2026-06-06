package model;

import java.io.Serializable;
import java.util.*;

public class Tratamento implements Serializable {

    private int idTratamento;
    private String tipo;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private Paciente pacientes;
    private List<Profissional> profissionais;
    public String getTipo() {
        return tipo;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getDataInicio() {
        return dataInicio;
    }
    public String getDataFim() {
        return dataFim;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public List<Profissional> getProfissionais() {
        return profissionais;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }
    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }
    public void setPacientes(Paciente pacientes) {
        this.pacientes = pacientes;
    }
    public void setProfissionais(List<Profissional> profissionais) {
        this.profissionais = profissionais;
    }
    public Tratamento(String tipo, String descricao, String dataInicio, String dataFim, Paciente pacientes,
            List<Profissional> profissionais) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.pacientes = pacientes;
        this.profissionais = profissionais;
    }

}
