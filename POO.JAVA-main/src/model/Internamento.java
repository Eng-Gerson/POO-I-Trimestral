package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Internamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int contador = 1;

    private int idInternamento;
    private String dataEntrada;
    private String dataSaida;
    private int quarto;
    private String motivo;
    private List<Profissional> profissionaisEnvolvidos;
    private Departamento departamento;
    private Paciente paciente;

    public Internamento(int quarto, String dataEntrada, String dataSaida, String motivo,
            Departamento departamento, Paciente paciente) {
        this.idInternamento = contador++;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.motivo = motivo;
        this.departamento = departamento;
        this.paciente = paciente;
        this.profissionaisEnvolvidos = new ArrayList<>();
    }

    // Sincroniza o contador com os dados carregados do ficheiro
    public static void sincronizarContador(List<Internamento> lista) {
        lista.stream()
             .mapToInt(Internamento::getIdInternamento)
             .max()
             .ifPresent(max -> contador = max + 1);
    }

    public int getIdInternamento() { return idInternamento; }
    public int getQuarto() { return quarto; }
    public String getDataEntrada() { return dataEntrada; }
    public String getDataSaida() { return dataSaida; }
    public String getMotivo() { return motivo; }
    public List<Profissional> getProfissionaisEnvolvidos() { return profissionaisEnvolvidos; }
    public Departamento getDepartamento() { return departamento; }
    public Paciente getPaciente() { return paciente; }

    public void setDataEntrada(String dataEntrada) { this.dataEntrada = dataEntrada; }
    public void setDataSaida(String dataSaida) { this.dataSaida = dataSaida; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public void setQuarto(int quarto) { this.quarto = quarto; }
    public void setProfissionaisEnvolvidos(List<Profissional> profissionaisEnvolvidos) { this.profissionaisEnvolvidos = profissionaisEnvolvidos; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
}
