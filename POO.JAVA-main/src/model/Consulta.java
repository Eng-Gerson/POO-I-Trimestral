package model;

import java.io.Serializable;
import java.util.List;

public class Consulta implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int contador = 1;

    private int idConsulta;
    private String data;
    private String horas;
    private String tipo;
    private String obrservasoesClinicas;
    private Paciente paciente;
    private Profissional profissional;
    private String diagnostico;

    public Consulta(String data, String horas, String tipo, String obrservasoesClinicas,
            Paciente paciente, Profissional profissional, String diagnostico) {
        this.idConsulta = contador++;
        this.data = data;
        this.horas = horas;
        this.tipo = tipo;
        this.obrservasoesClinicas = obrservasoesClinicas;
        this.paciente = paciente;
        this.profissional = profissional;
        this.diagnostico = diagnostico;
    }

    // Sincroniza o contador com os dados carregados do ficheiro
    public static void sincronizarContador(List<Consulta> lista) {
        lista.stream()
             .mapToInt(Consulta::getIdConsulta)
             .max()
             .ifPresent(max -> contador = max + 1);
    }

    public int getIdConsulta() { return idConsulta; }
    public String getData() { return data; }
    public String getHoras() { return horas; }
    public String getTipo() { return tipo; }
    public String getObrservasoesClinicas() { return obrservasoesClinicas; }
    public Paciente getPaciente() { return paciente; }
    public Profissional getProfissional() { return profissional; }
    public String getDiagnostico() { return diagnostico; }

    public void setData(String data) { this.data = data; }
    public void setHoras(String horas) { this.horas = horas; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setObrservasoesClinicas(String obrservasoesClinicas) { this.obrservasoesClinicas = obrservasoesClinicas; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setProfissional(Profissional profissional) { this.profissional = profissional; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
}
