package model;
import model.Paciente;
import java.util.*;

public class Profissional extends Pessoa{
    protected String idProfissional;
    protected Departamento departamento;
    protected List<String> contacto;
    protected char genero;
    protected String nome;
    protected List<Paciente> pacientes;
    protected String numeroCedulaProfissional;

    public Profissional(String idProfissional, Departamento departamento, List<String> contacto, char genero, String nome,
            List<Paciente> pacientes, String numeroCedulaProfissional) {
        this.idProfissional = idProfissional;
        this.departamento = departamento;
        this.contacto = contacto;
        this.genero = genero;
        this.nome = nome;
        this.pacientes = pacientes;
        this.numeroCedulaProfissional = numeroCedulaProfissional;
    }
    public String getIdProfissional() {
        return idProfissional;
    }
    public Departamento getDepartamento() {
        return departamento;
    }
    public List<String> getContacto() {
        return contacto;
    }
    public char getGenero() {
        return genero;
    }
    public String getNome() {
        return nome;
    }
    public List<Paciente> getPacientes() {
        return pacientes;
    }
    
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    public void setContacto(List<String> contacto) {
        this.contacto = contacto;
    }
    public void setGenero(char genero) {
        this.genero = genero;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public String getNumeroCedulaProfissional() {
        return numeroCedulaProfissional;
    }


}
