package model;

import java.io.Serializable;
import java.util.List;

public abstract class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String nome;
    protected char genero;
    protected List<String> contacto;

    public Pessoa(String nome, char genero, List<String> contacto) {
        this.nome = nome;
        this.genero = genero;
        this.contacto = contacto;
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public List<String> getContacto() {
        return contacto;
    }

    public void setContacto(List<String> contacto) {
        this.contacto = contacto;
    }
}
