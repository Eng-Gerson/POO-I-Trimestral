package menu;

import io.ConsoleInput;
import service.DepartamentoService;
import exception.EntidadeNaoEncontradaException;
import model.*;

import java.util.List;

public class MenuDepartamento {
    private static DepartamentoService service = new DepartamentoService();

    public static void exibir() {
        int op = -1;
        do {
            System.out.println("\n--- MENU DEPARTAMENTOS ---");
            System.out.println("1. Cadastrar Departamento");
            System.out.println("2. Listar todos os departamentos");
            System.out.println("3. Ver dados de um departamento");
            System.out.println("4. Remover Departamento");
            System.out.println("0. Voltar");

            op = ConsoleInput.lerInteiro("Opção: ");

            switch (op) {
                case 1: cadastrar(); break;
                case 2: listarTodos(); break;
                case 3: verDados(); break;
                case 4: remover(); break;
                case 0: System.out.println("A voltar ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void cadastrar() {
        System.out.println("\n--- CADASTRAR DEPARTAMENTO ---");
        try {
            String nome = ConsoleInput.lerString("Nome do Departamento: ");

            Departamento d = new Departamento(nome);
            service.cadastrarDepartamento(d);
            System.out.println("Sucesso: Departamento cadastrado com sucesso! ID atribuído: " + d.getIdDepartamento());

        } catch (IllegalArgumentException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarTodos() {
        System.out.println("\n--- LISTA DE DEPARTAMENTOS ---");
        List<Departamento> lista = service.listarDepartamentos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum departamento registado até ao momento.");
            return;
        }

        lista.forEach(d ->
            System.out.println("ID: " + d.getIdDepartamento() + " | Nome: " + d.getNomeDepartamento()
                + " | Profissionais: " + d.getProfissionaisDoDepartamento().size()));
    }

    private static void verDados() {
        System.out.println("\n--- VER DADOS DO DEPARTAMENTO ---");
        int id = ConsoleInput.lerInteiro("ID do departamento: ");

        try {
            Departamento d = service.buscarDepartamentoPorId(id);

            System.out.println("\n--- DETALHES ---");
            System.out.println("ID: " + d.getIdDepartamento());
            System.out.println("Nome: " + d.getNomeDepartamento());
            System.out.println("Profissionais: " + d.getProfissionaisDoDepartamento().size());

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void remover() {
        System.out.println("\n--- REMOVER DEPARTAMENTO ---");
        int id = ConsoleInput.lerInteiro("ID do departamento a remover: ");

        try {
            service.removerDepartamento(id);
            System.out.println("Sucesso: Departamento ID " + id + " removido com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }
}
