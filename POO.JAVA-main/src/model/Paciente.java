package model;

import java.util.*;
import validation.ValidadorData;

public class Paciente extends Pessoa {

    private int idPaciente;
    private int altura;
    private float peso;
    private String dataNacimento; // Campo obrigatório para cálculo de idade
    private String endereco;
    private List<String> contactoEmergencia;
    private List<Consulta> consultas;
    private List<Exame> exames;
    private List<Tratamento> tratamentos;
    private List<Internamento> internamentos;

    // Construtor atualizado sem o parâmetro idade
    public Paciente(int idPaciente, int altura, float peso, String nome, String dataNacimento, char genero,
            String endereco, List<String> contacto, List<String> contactoEmergencia, List<Consulta> consultas,
            List<Exame> exames, List<Tratamento> tratamentos, List<Internamento> internamentos) {
        super(nome, genero, contacto);
        this.idPaciente = idPaciente;
        this.altura = altura;
        this.peso = peso;
        this.dataNacimento = dataNacimento;
        this.endereco = endereco;
        this.contactoEmergencia = contactoEmergencia;
        this.consultas = consultas;
        this.exames = exames;
        this.tratamentos = tratamentos;
        this.internamentos = internamentos;
    }

    public int getIdPaciente() {
        return idPaciente;
    }
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * Calcula a idade dinamicamente a partir da data de nascimento
     */
    public int getIdade() {
        if (dataNacimento == null || dataNacimento.isEmpty()) {
            return 0;
        }
        try {
            ValidadorData validador = new ValidadorData();
            return validador.calcularIdade(dataNacimento);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    public String getDataNacimento() {
        return dataNacimento;
    }
    public void setDataNacimento(String dataNacimento) {
        this.dataNacimento = dataNacimento;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<String> getContactoEmergencia() {
        return contactoEmergencia;
    }
    public void setContactoEmergencia(List<String> contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }
    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<Exame> getExames() {
        return exames;
    }
    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    public List<Tratamento> getTratamentos() {
        return tratamentos;
    }
    public void setTratamentos(List<Tratamento> tratamentos) {
        this.tratamentos = tratamentos;
    }

    public List<Internamento> getInternamentos() {
        return internamentos;
    }
    public void setInternamentos(List<Internamento> internamentos) {
        this.internamentos = internamentos;
    }

    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }
    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void marcarConsulta(Consulta c) {
        // Implementar quando necessário
    }

    public void cancelarConsulta(String codigo) {
        // Implementar quando necessário
    }

    public void reagendarConsulta(String codConsulta, String novaData, String novaHora) {
        // Implementar quando necessário
    }

    public List<Consulta> listarConsultas() {
        return this.consultas;
    }

    public float calcularIMC() {
        return this.peso / (this.altura * this.altura / 10000f); // Altura em cm, dividir por 10000
    }

    public void verificarIMC() {
        float imc = calcularIMC();
        if (imc < 18.5) {
            System.out.println("Abaixo do Peso");
        } else if (imc < 24.9) {
            System.out.println("Peso normal");
        } else if (imc < 29.9) {
            System.out.println("Um pouco acima do peso.");
        } else if (imc < 34.9) {
            System.out.println("Obesidade de Grau I");
        } else if (imc < 39.9) {
            System.out.println("Obesidade de Grau II (severa)");
        } else {
            System.out.println("Obesidade de Grau III (morbida)");
        }
    }
}
