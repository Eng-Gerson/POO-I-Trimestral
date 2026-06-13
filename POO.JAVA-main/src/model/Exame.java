package model;

import java.io.Serializable;
import java.util.List;

public class Exame implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int contador = 1;

    private int idExame;
    private String tipo;
    private String data;
    private String resultado;
    private String observacoes;
    private Paciente paciente;
    private Consulta consulta;

    public Exame(String tipo, String data, String resultado, String observacoes,
            Paciente paciente, Consulta consulta) {
        this.idExame = contador++;
        this.tipo = tipo;
        this.data = data;
        this.resultado = resultado;
        this.observacoes = observacoes;
        this.paciente = paciente;
        this.consulta = consulta;
    }

    // Sincroniza o contador com os dados carregados do ficheiro
    public static void sincronizarContador(List<Exame> lista) {
        lista.stream()
             .mapToInt(Exame::getIdExame)
             .max()
             .ifPresent(max -> contador = max + 1);
    }

    public int getIdExame() { return idExame; }
    public String getTipo() { return tipo; }
    public String getData() { return data; }
    public String getResultado() { return resultado; }
    public String getObservacoes() { return observacoes; }
    public Paciente getPaciente() { return paciente; }
    public Consulta getConsulta() { return consulta; }

    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setData(String data) { this.data = data; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }
}
