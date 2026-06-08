package menu;

import io.ConsoleInput;
import service.ProfissionalService;
import service.DepartamentoService;
import exception.EntidadeNaoEncontradaException;
import exception.ProfissionalInvalidoException;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuProfissional {
    private static ProfissionalService service = new ProfissionalService();
    private static DepartamentoService departamentoService = new DepartamentoService();

    public static void exibir() {
        int op = -1;
        do {
            System.out.println("\n--- MENU PROFISSIONAIS ---");
            System.out.println("1. Cadastrar Profissional");
            System.out.println("2. Listar todos (Resumo)");
            System.out.println("3. Ver dados completos de um profissional");
            System.out.println("4. Ver dados parciais de um profissional");
            System.out.println("5. Remover Profissional");
            System.out.println("0. Voltar");

            op = ConsoleInput.lerInteiro("Opção: ");

            switch (op) {
                case 1: cadastrar(); break;
                case 2: listarResumo(); break;
                case 3: verDadosCompletos(); break;
                case 4: verDadosParciais(); break;
                case 5: remover(); break;
                case 0: System.out.println("A voltar ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void cadastrar() {
        System.out.println("\n--- CADASTRAR PROFISSIONAL ---");
        try {
            int id        = ConsoleInput.lerInteiro("ID: ");
            String nome   = ConsoleInput.lerString("Nome: ");
            char genero   = ConsoleInput.lerString("Género (M/F): ").charAt(0);
            String cedula = ConsoleInput.lerString("Número da Cédula: ");
            String contacto = ConsoleInput.lerString("Contacto: ");
            
            System.out.println("\n--- DEPARTAMENTOS DISPONÍVEIS ---");
            List<Departamento> departamentos = departamentoService.listarDepartamentos();
            
            if (departamentos.isEmpty()) {
                System.out.println("Nenhum departamento registado no sistema.");
                System.out.println("Por favor, registre um departamento antes de cadastrar um profissional.");
                return;
            }
            
            departamentos.forEach(d -> 
                System.out.println("ID: " + d.getIdDepartamento() + " | Nome: " + d.getNomeDepartamento()));
            
            int idDept = ConsoleInput.lerInteiro("\nSelecione o ID do departamento: ");
            Departamento depto = departamentoService.buscarDepartamentoPorId(idDept);

            Profissional p = new Profissional(
                id, depto,
                new ArrayList<>(Arrays.asList(contacto)),
                genero, nome, new ArrayList<>(), cedula
            );

            service.cadastrarProfissional(p);
            System.out.println("Sucesso: Profissional cadastrado com sucesso!");

        } catch (ProfissionalInvalidoException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarResumo() {
        System.out.println("\n--- LISTA DE PROFISSIONAIS ---");
        List<Profissional> lista = service.listarProfissionais();

        if (lista.isEmpty()) {
            System.out.println("Nenhum profissional registado até ao momento.");
            return;
        }

        lista.forEach(p ->
            System.out.println("ID: " + p.getIdProfissional() + " | Nome: " + p.getNome()
                + " | Cédula: " + p.getNumeroCedulaProfissional()
                + " | Depto: " + (p.getDepartamento() != null ? p.getDepartamento().getNomeDepartamento() : "N/A")));
    }

    private static void verDadosCompletos() {
        System.out.println("\n--- VER DADOS DO PROFISSIONAL ---");
        int id = ConsoleInput.lerInteiro("ID do profissional: ");

        try {
            Profissional p = service.buscarProfissionalPorId(id);

            System.out.println("\n--- DADOS COMPLETOS ---");
            System.out.println("ID: " + p.getIdProfissional());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Cédula: " + p.getNumeroCedulaProfissional());
            System.out.println("Género: " + p.getGenero());
            System.out.println("Contactos: " + p.getContacto());
            if (p.getDepartamento() != null) {
                System.out.println("Departamento: " + p.getDepartamento().getNomeDepartamento());
                System.out.println("ID Departamento: " + p.getDepartamento().getIdDepartamento());
            } else {
                System.out.println("Departamento: Não atribuído");
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void verDadosParciais() {
        System.out.println("\n--- VER DADOS PARCIAIS DO PROFISSIONAL ---");
        int id = ConsoleInput.lerInteiro("ID do profissional: ");

        try {
            Profissional p = service.buscarProfissionalPorId(id);

            System.out.println("\n--- DADOS PARCIAIS ---");
            System.out.println("Nome: " + p.getNome());
            System.out.println("Cédula Profissional: " + p.getNumeroCedulaProfissional());

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void remover() {
        System.out.println("\n--- REMOVER PROFISSIONAL ---");
        int id = ConsoleInput.lerInteiro("ID do profissional a remover: ");

        try {
            service.removerProfissional(id);
            System.out.println("Sucesso: Profissional ID " + id + " removido com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }
}
