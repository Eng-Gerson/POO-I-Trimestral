package model;

import java.io.Serializable;
import java.util.List;

public class Tratamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int contador = 1;

    private int idTratamento;
    private String tipo;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private Paciente paciente;
    private List<Profissional> profissionais;
    private boolean concluido;

    public Tratamento(String tipo, String descricao, String dataInicio, String dataFim,
                      Paciente paciente, List<Profissional> profissionais) {
        this.idTratamento = contador++;
        this.tipo = tipo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.paciente = paciente;
        this.profissionais = profissionais;
        this.concluido = false;
    }

    // Sincroniza o contador com os dados carregados do ficheiro
    public static void sincronizarContador(List<Tratamento> lista) {
        lista.stream()
             .mapToInt(Tratamento::getIdTratamento)
             .max()
             .ifPresent(max -> contador = max + 1);
    }

    public int getIdTratamento() { return idTratamento; }

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

    public boolean isConcluido() { return concluido; }
    public void setConcluido(boolean concluido) { this.concluido = concluido; }

    public void concluir() { this.concluido = true; }

    public String getStatus() {
        if (concluido) {
            return "Concluído";
        } else if (dataFim != null && !dataFim.isEmpty()) {
            return "Finalizado";
        } else {
            return "Em andamento";
        }
    }
}
