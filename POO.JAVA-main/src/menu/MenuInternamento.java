package menu;

import io.ConsoleInput;
import service.InternamentoService;
import service.PacienteService;
import exception.EntidadeNaoEncontradaException;
import exception.InternamentoInvalidoException;
import model.*;

import java.util.List;

public class MenuInternamento {
    private static InternamentoService service = new InternamentoService();
    private static PacienteService pacienteService = new PacienteService();

    public static void exibir() {
        int op = -1;
        do {
            System.out.println("\n--- MENU INTERNAMENTOS ---");
            System.out.println("1. Registar Novo Internamento");
            System.out.println("2. Listar todos os internamentos");
            System.out.println("3. Ver dados de um internamento");
            System.out.println("4. Listar internamentos de um Paciente");
            System.out.println("5. Registar Alta de um Internamento");
            System.out.println("0. Voltar");

            op = ConsoleInput.lerInteiro("Opção: ");

            switch (op) {
                case 1: registarInternamento(); break;
                case 2: listarTodos(); break;
                case 3: buscarInternamento(); break;
                case 4: listarPorPaciente(); break;
                case 5: registarAlta(); break;
                case 0: System.out.println("A voltar ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void registarInternamento() {
        System.out.println("\n--- REGISTAR NOVO INTERNAMENTO ---");
        try {
            int quarto     = ConsoleInput.lerInteiro("Número do Quarto: ");
            String entrada = ConsoleInput.lerString("Data de Entrada (dd/mm/aaaa): ");
            String saida   = ConsoleInput.lerString("Data de Saída (ou 'Pendente'): ");
            String motivo  = ConsoleInput.lerString("Motivo: ");

            int idPac  = ConsoleInput.lerInteiro("ID do Paciente: ");
            Paciente p = pacienteService.buscarPacientePorId(idPac);

            Internamento i = new Internamento(quarto, entrada, saida, motivo, null, p);
            service.registrarInternamento(i);
            System.out.println("Sucesso: Internamento registado com sucesso! ID atribuído: " + i.getIdInternamento());

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO DE REGISTO]: " + e.getMessage());
        } catch (InternamentoInvalidoException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarTodos() {
        System.out.println("\n--- LISTA DE INTERNAMENTOS ---");
        List<Internamento> lista = service.listarHistorico();

        if (lista.isEmpty()) {
            System.out.println("Nenhum internamento registado até ao momento.");
            return;
        }

        lista.forEach(i ->
            System.out.println("ID: " + i.getIdInternamento()
                + " | Paciente: " + i.getPaciente().getNome()
                + " | Quarto: " + i.getQuarto()
                + " | Motivo: " + i.getMotivo()));
    }

    private static void buscarInternamento() {
        System.out.println("\n--- VER DADOS DO INTERNAMENTO ---");
        int id = ConsoleInput.lerInteiro("ID do Internamento: ");

        try {
            Internamento i = service.buscarPorId(id);

            System.out.println("\n--- DETALHES DO INTERNAMENTO ---");
            System.out.println("ID: " + i.getIdInternamento());
            System.out.println("Paciente: " + i.getPaciente().getNome());
            System.out.println("Quarto: " + i.getQuarto());
            System.out.println("Entrada: " + i.getDataEntrada() + " | Saída: " + i.getDataSaida());
            System.out.println("Motivo: " + i.getMotivo());
            if (i.getDepartamento() != null) {
                System.out.println("Departamento: " + i.getDepartamento().getNomeDepartamento());
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarPorPaciente() {
        System.out.println("\n--- INTERNAMENTOS DE UM PACIENTE ---");
        int id = ConsoleInput.lerInteiro("ID do Paciente: ");

        try {
            pacienteService.buscarPacientePorId(id);

            long total = service.listarHistorico().stream()
                .filter(i -> i.getPaciente().getIdPaciente() == id)
                .peek(i -> System.out.println(" - ID: " + i.getIdInternamento()
                    + " | Data: " + i.getDataEntrada()
                    + " | Motivo: " + i.getMotivo()))
                .count();

            if (total == 0) {
                System.out.println("Nenhum internamento encontrado para este paciente.");
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void registarAlta() {
        System.out.println("\n--- REGISTAR ALTA ---");
        int id = ConsoleInput.lerInteiro("ID do Internamento: ");

        try {
            String dataSaida = ConsoleInput.lerString("Data de Alta (dd/mm/aaaa): ");
            service.registrarAlta(id, dataSaida);
            System.out.println("Sucesso: Alta registada com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (InternamentoInvalidoException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }
}
