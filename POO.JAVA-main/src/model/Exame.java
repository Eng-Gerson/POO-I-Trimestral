package model;

import java.io.Serializable;

public class Exame implements Serializable {

    private String tipo;
    private String idExame;
    private String data;
    private String resultado;
    private String observacoes;
    private Paciente paciente;
    private Consulta consulta;

    public Exame(String tipo, String idExame, String data, String resultado, String observacoes, Paciente paciente, Consulta consulta) {
        this.tipo = tipo;
        this.idExame = idExame;
        this.data = data;
        this.resultado = resultado;
        this.observacoes = observacoes;
        this.paciente = paciente;
        this.consulta = consulta;
    }

    public String getIdExame() {
        return idExame;
    }
    public String getTipo() {
        return tipo;
    }
    public String getData() {
        return data;
    }
    public String getResultado() {
        return resultado;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public Consulta getConsulta() {
        return consulta;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
