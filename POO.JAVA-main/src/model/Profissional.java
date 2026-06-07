package model;

import java.util.*;

public class Profissional extends Pessoa{

    protected int idProfissional;
    protected Departamento departamento;
    protected List<Paciente> pacientes;
    protected String numeroCedulaProfissional;

    public Profissional(int idProfissional, Departamento departamento, List<String> contacto, char genero, String nome,
            List<Paciente> pacientes, String numeroCedulaProfissional) {
        super(nome, genero, contacto);
        this.idProfissional = idProfissional;
        this.departamento = departamento;
        this.pacientes = pacientes;
        this.numeroCedulaProfissional = numeroCedulaProfissional;
    }

    public int getIdProfissional() {
        return idProfissional;
    }
    public Departamento getDepartamento() {
        return departamento;
    }
    public List<Paciente> getPacientes() {
        return pacientes;
    }
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
    public String getNumeroCedulaProfissional() {
        return numeroCedulaProfissional;
    }


}
