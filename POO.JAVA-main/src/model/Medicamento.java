package model;

import java.io.Serializable;
import java.util.*;

public class Medicamento implements Serializable {

   private int idMedicamento;
   private String nome;
   private String dosagem;
   private List<String> dataAplicacao;

   public Medicamento(int idMedicamento, List<String> dataAplicacao) {
    this.idMedicamento = idMedicamento;
    this.nome = nome;
    this.dosagem = dosagem;
    this.dataAplicacao = dataAplicacao;
   }

   public int getIdMedicamento() {
    return idMedicamento;
   }
   public String getNome() {
    return nome;
   }
   public String getDosagem() {
    return dosagem;
   }
   public List<String> getDataAplicacao(){
    return dataAplicacao;
   }
   public void setIdMedicamento(int idMedicamento) {
    this.idMedicamento = idMedicamento;
   }
   public void setNome(String nome) {
    this.nome = nome;
   }
   public void setDosagem(String dosagem) {
    this.dosagem = dosagem;
   }
   public void setDataAplicacao( List<String> dataAplicacao) {
    this.dataAplicacao = dataAplicacao;
   }

}
