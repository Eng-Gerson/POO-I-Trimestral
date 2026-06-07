package menu;

import service.PacienteService;
import model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuPaciente {
    private static PacienteService service = new PacienteService();

    public static void exibir(BufferedReader br) throws IOException {
        int subOpcao = -1;
        do {
            System.out.println("\n--- MENU PACIENTES ---");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Listar todos");
            System.out.println("3. Ver dados completos");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            
            subOpcao = Integer.parseInt(br.readLine());

            switch (subOpcao) {
                case 1: cadastrar(br); break;
                case 2: listarResumo(); break;
                case 3: verDadosCompletos(br); break;
            }
        } while (subOpcao != 0);
    }

    private static void cadastrar(BufferedReader br) throws IOException {
        System.out.print("ID: "); int id = Integer.parseInt(br.readLine());
        System.out.print("Nome: "); String nome = br.readLine();
        System.out.print("Idade: "); int idade = Integer.parseInt(br.readLine());
        System.out.print("Altura (cm): "); int altura = Integer.parseInt(br.readLine());
        System.out.print("Peso (kg): "); float peso = Float.parseFloat(br.readLine());
        System.out.print("Data de Nascimento: "); String dataNasc = br.readLine();
        System.out.print("Género (M/F): "); char genero = br.readLine().charAt(0);
        System.out.print("Endereço: "); String endereco = br.readLine();
        System.out.print("Contacto: "); String contacto = br.readLine();
        System.out.print("Contacto de Emergência: "); String contatoEmerg = br.readLine();

        // Criando o objeto com o teu construtor complexo
        Paciente p = new Paciente(
            id, altura, peso, idade, nome, dataNasc, genero, endereco,
            new ArrayList<>(Arrays.asList(contacto)),
            new ArrayList<>(Arrays.asList(contatoEmerg)),
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
        
        service.cadastrarPaciente(p);
        System.out.println("Paciente cadastrado com sucesso!");
    }

    private static void listarResumo() {
        service.listarPacientes().forEach(p -> 
            System.out.println("ID: " + p.getIdPaciente() + " | Nome: " + p.getNome()));
    }

    private static void verDadosCompletos(BufferedReader br) throws IOException {
        System.out.print("ID do paciente: ");
        int id = Integer.parseInt(br.readLine());
        Paciente p = service.buscarPacientePorId(id);
        
        if (p != null) {
            System.out.println("\n--- FICHA COMPLETA ---");
            System.out.println("Nome: " + p.getNome());
            System.out.println("Idade: " + p.getIdade() + " anos");
            System.out.println("Peso: " + p.getPeso() + "kg | Altura: " + p.getAltura() + "cm");
            System.out.println("Endereço: " + p.getEndereco());
            System.out.println("Contacto: " + p.getContacto());
            System.out.println("Emergência: " + p.getContactoEmergencia());
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }
}