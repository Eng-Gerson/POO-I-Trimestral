package model;

import java.io.Serializable;
import java.util.List;

public class Tratamento implements Serializable {
    private static final long serialVersionUID = 1L; // Recomenda-se para classes Serializable

    private int idTratamento;
    private String tipo;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private Paciente paciente;
    private List<Profissional> profissionais;

    // Construtor atualizado para incluir o idTratamento
    public Tratamento(int idTratamento, String tipo, String descricao, String dataInicio, String dataFim, 
                      Paciente paciente, List<Profissional> profissionais) {
        this.idTratamento = idTratamento;
        this.tipo = tipo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.paciente = paciente;
        this.profissionais = profissionais;
    }

    // Getters e Setters
    public int getIdTratamento() { return idTratamento; }
    public void setIdTratamento(int idTratamento) { this.idTratamento = idTratamento; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public List<Profissional> getProfissionais() { return profissionais; }
    public void setProfissionais(List<Profissional> profissionais) { this.profissionais = profissionais; }
}