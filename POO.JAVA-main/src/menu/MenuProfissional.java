package menu;

import service.ProfissionalService;
import service.DepartamentoService;
import model.Profissional;
import model.Departamento;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuProfissional {
    private static ProfissionalService profService = new ProfissionalService();
    private static DepartamentoService deptService = new DepartamentoService();

    public static void exibir(BufferedReader br) throws IOException {
        int subOpcao = -1;
        do {
            System.out.println("\n--- MENU PROFISSIONAIS ---");
            System.out.println("1. Cadastrar Profissional");
            System.out.println("2. Listar todos (Resumo)");
            System.out.println("3. Ver todos os dados de um profissional");
            System.out.println("4. Ver apenas certos dados de um profissional");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            
            subOpcao = Integer.parseInt(br.readLine());

            switch (subOpcao) {
                case 1: cadastrar(br); break;
                case 2: listarResumo(); break;
                case 3: verDadosCompletos(br); break;
                case 4: verDadosParciais(br); break;
            }
        } while (subOpcao != 0);
    }

    private static void cadastrar(BufferedReader br) throws IOException {
        System.out.print("ID: "); int id = Integer.parseInt(br.readLine());
        System.out.print("Nome: "); String nome = br.readLine();
        System.out.print("Género (M/F): "); char genero = br.readLine().charAt(0);
        System.out.print("Número da Cédula: "); String cedula = br.readLine();
        System.out.print("Contacto: "); String contacto = br.readLine();
        System.out.print("ID do Departamento: "); int idDept = Integer.parseInt(br.readLine());
        
        Departamento dept = deptService.buscarDepartamentoPorId(idDept);
        if (dept == null) { System.out.println("Erro: Dept não encontrado!"); return; }

        Profissional p = new Profissional(id, dept, new ArrayList<>(Arrays.asList(contacto)), 
                                          genero, nome, new ArrayList<>(), cedula);
        profService.cadastrarProfissional(p);
    }

    private static void listarResumo() {
        profService.listarProfissionais().forEach(p -> 
            System.out.println("ID: " + p.getIdProfissional() + " | Nome: " + p.getNome()));
    }

    private static void verDadosCompletos(BufferedReader br) throws IOException {
        System.out.print("Digite o ID do profissional: ");
        int id = Integer.parseInt(br.readLine());
        Profissional p = profService.buscarProfissionalPorId(id);
        if (p != null) {
            System.out.println("\n--- DADOS COMPLETOS ---");
            System.out.println("Nome: " + p.getNome());
            System.out.println("Cédula: " + p.getNumeroCedulaProfissional());
            System.out.println("Género: " + p.getGenero());
            System.out.println("Departamento: " + p.getDepartamento().getNomeDepartamento());
            System.out.println("Contactos: " + p.getContacto());
        } else {
            System.out.println("Profissional não encontrado.");
        }
    }

    private static void verDadosParciais(BufferedReader br) throws IOException {
        System.out.print("Digite o ID do profissional: ");
        int id = Integer.parseInt(br.readLine());
        Profissional p = profService.buscarProfissionalPorId(id);
        if (p != null) {
            System.out.println("\n--- DADOS PARCIAIS ---");
            System.out.println("Nome: " + p.getNome());
            System.out.println("Cédula Profissional: " + p.getNumeroCedulaProfissional());
        } else {
            System.out.println("Profissional não encontrado.");
        }
    }
}