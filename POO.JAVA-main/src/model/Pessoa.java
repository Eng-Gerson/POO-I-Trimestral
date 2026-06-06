package model;

import java.io.Serializable;
import java.util.List;

public class Pessoa implements Serializable {

    protected String nome;
    protected char genero;
    protected List<String> contacto;

    public Pessoa(String nome, char genero, List<String> contacto) {
        this.nome = nome;
        this.genero = genero;
        this.contacto = contacto;
    }

    public String getNome() {
        return nome;
    }
    public char getGenero() {
        return genero;
    }
    public List<String> getContacto() {
        return contacto;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setGenero(char genero) {
        this.genero = genero;
    }
    public void setContacto(List<String> contacto) {
        this.contacto = contacto;
    }

}
