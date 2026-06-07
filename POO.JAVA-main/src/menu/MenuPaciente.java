package menu;

import io.ConsoleInput;
import service.PacienteService;
import exception.EntidadeNaoEncontradaException;
import exception.PacienteInvalidoException;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuPaciente {
    private static PacienteService service = new PacienteService();

    public static void exibir() {
        int op = -1;
        do {
            System.out.println("\n--- MENU PACIENTES ---");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Listar todos (Resumo)");
            System.out.println("3. Ver dados completos de um paciente");
            System.out.println("4. Remover Paciente");
            System.out.println("0. Voltar");

            op = ConsoleInput.lerInteiro("Opção: ");

            switch (op) {
                case 1: cadastrar(); break;
                case 2: listarResumo(); break;
                case 3: verDadosCompletos(); break;
                case 4: remover(); break;
                case 0: System.out.println("A voltar ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void cadastrar() {
        System.out.println("\n--- CADASTRAR PACIENTE ---");
        try {
            int id          = ConsoleInput.lerInteiro("ID: ");
            String nome     = ConsoleInput.lerString("Nome: ");
            int idade       = ConsoleInput.lerInteiro("Idade: ");
            int altura      = ConsoleInput.lerInteiro("Altura (cm): ");
            float peso      = (float) ConsoleInput.lerDecimal("Peso (kg): ");
            String dataNasc = ConsoleInput.lerString("Data de Nascimento (dd/mm/aaaa): ");
            char genero     = ConsoleInput.lerString("Género (M/F): ").charAt(0);
            String endereco = ConsoleInput.lerString("Endereço: ");
            String contacto = ConsoleInput.lerString("Contacto: ");
            String contatoEmerg = ConsoleInput.lerString("Contacto de Emergência: ");

            Paciente p = new Paciente(
                id, altura, peso, idade, nome, dataNasc, genero, endereco,
                new ArrayList<>(Arrays.asList(contacto)),
                new ArrayList<>(Arrays.asList(contatoEmerg)),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            );

            service.cadastrarPaciente(p);
            System.out.println("Sucesso: Paciente cadastrado com sucesso!");

        } catch (PacienteInvalidoException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarResumo() {
        System.out.println("\n--- LISTA DE PACIENTES ---");
        List<Paciente> lista = service.listarPacientes();

        if (lista.isEmpty()) {
            System.out.println("Nenhum paciente registado até ao momento.");
            return;
        }

        lista.forEach(p ->
            System.out.println("ID: " + p.getIdPaciente() + " | Nome: " + p.getNome() + " | Idade: " + p.getIdade()));
    }

    private static void verDadosCompletos() {
        System.out.println("\n--- VER DADOS DO PACIENTE ---");
        int id = ConsoleInput.lerInteiro("ID do paciente: ");

        try {
            Paciente p = service.buscarPacientePorId(id);

            System.out.println("\n--- FICHA COMPLETA ---");
            System.out.println("ID: " + p.getIdPaciente());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Idade: " + p.getIdade() + " anos");
            System.out.println("Peso: " + p.getPeso() + "kg | Altura: " + p.getAltura() + "cm");
            System.out.println("Data de Nascimento: " + p.getDataNacimento());
            System.out.println("Género: " + p.getGenero());
            System.out.println("Endereço: " + p.getEndereco());
            System.out.println("Contacto: " + p.getContacto());
            System.out.println("Emergência: " + p.getContactoEmergencia());

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void remover() {
        System.out.println("\n--- REMOVER PACIENTE ---");
        int id = ConsoleInput.lerInteiro("ID do paciente a remover: ");

        try {
            service.removerPaciente(id);
            System.out.println("Sucesso: Paciente ID " + id + " removido com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }
}
