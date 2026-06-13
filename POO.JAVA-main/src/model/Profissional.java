package model;

import java.util.*;

public class Profissional extends Pessoa {

    private static int contador = 1;

    protected int idProfissional;
    protected Departamento departamento;
    protected List<Paciente> pacientes;
    protected String numeroCedulaProfissional;

    public Profissional(Departamento departamento, List<String> contacto, char genero, String nome,
            List<Paciente> pacientes, String numeroCedulaProfissional) {
        super(nome, genero, contacto);
        this.idProfissional = contador++;
        this.departamento = departamento;
        this.pacientes = pacientes;
        this.numeroCedulaProfissional = numeroCedulaProfissional;
    }

    // Sincroniza o contador com os dados carregados do ficheiro
    public static void sincronizarContador(List<Profissional> lista) {
        lista.stream()
             .mapToInt(Profissional::getIdProfissional)
             .max()
             .ifPresent(max -> contador = max + 1);
    }

    public int getIdProfissional() { return idProfissional; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public List<Paciente> getPacientes() { return pacientes; }
    public void setPacientes(List<Paciente> pacientes) { this.pacientes = pacientes; }

    public String getNumeroCedulaProfissional() { return numeroCedulaProfissional; }
}
