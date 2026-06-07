package menu;

import io.ConsoleInput;
import service.ConsultaService;
import service.ExameService;
import service.PacienteService;
import exception.EntidadeNaoEncontradaException;
import exception.ExameInvalidoException;
import model.*;

import java.util.List;

public class MenuExame {
    private static ConsultaService consultaService = new ConsultaService();
    private static ExameService exameService = new ExameService(consultaService);
    private static PacienteService pacienteService = new PacienteService();

    public static void exibir() {
        int op = -1;
        do {
            System.out.println("\n--- MENU EXAMES ---");
            System.out.println("1. Marcar Exame");
            System.out.println("2. Buscar Exame por ID");
            System.out.println("3. Listar Exames de um Paciente");
            System.out.println("4. Listar Consultas de um Paciente");
            System.out.println("5. Listar Consultas de um Profissional");
            System.out.println("6. Listar Consultas por Data");
            System.out.println("0. Voltar");

            op = ConsoleInput.lerInteiro("Opção: ");

            switch (op) {
                case 1: cadastrarExame(); break;
                case 2: buscarExame(); break;
                case 3: listarExamesPorPaciente(); break;
                case 4: listarConsultasPorPaciente(); break;
                case 5: listarConsultasPorProfissional(); break;
                case 6: listarConsultasPorData(); break;
                case 0: System.out.println("A voltar ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void cadastrarExame() {
        System.out.println("\n--- MARCAR EXAME ---");
        try {
            int id   = ConsoleInput.lerInteiro("ID do Exame: ");
            String tipo = ConsoleInput.lerString("Tipo (ex: Hemograma, Raio-X): ");
            String data = ConsoleInput.lerString("Data (dd/mm/aaaa): ");

            int idCons    = ConsoleInput.lerInteiro("ID da Consulta Associada: ");
            Consulta c    = consultaService.buscarConsultaPorId(idCons);

            Exame e = new Exame(tipo, id, data, "Pendente", "N/A", c.getPaciente(), c);
            exameService.solicitarExame(e);
            System.out.println("Sucesso: Exame marcado com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO DE REGISTO]: " + e.getMessage());
        } catch (ExameInvalidoException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void buscarExame() {
        System.out.println("\n--- BUSCAR EXAME ---");
        int id = ConsoleInput.lerInteiro("ID do Exame: ");

        try {
            Exame e = exameService.buscarExamePorId(id);

            System.out.println("\n--- DADOS DO EXAME ---");
            System.out.println("ID: " + e.getIdExame());
            System.out.println("Tipo: " + e.getTipo());
            System.out.println("Data: " + e.getData());
            System.out.println("Resultado: " + e.getResultado());
            System.out.println("Observações: " + e.getObservacoes());
            System.out.println("Paciente: " + e.getPaciente().getNome());

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarExamesPorPaciente() {
        System.out.println("\n--- EXAMES DE UM PACIENTE ---");
        int idP = ConsoleInput.lerInteiro("ID do Paciente: ");

        try {
            pacienteService.buscarPacientePorId(idP);

            long total = exameService.listarExames().stream()
                .filter(e -> e.getPaciente().getIdPaciente() == idP)
                .peek(e -> System.out.println(" - ID: " + e.getIdExame()
                    + " | Tipo: " + e.getTipo()
                    + " | Data: " + e.getData()
                    + " | Resultado: " + e.getResultado()))
                .count();

            if (total == 0) {
                System.out.println("Nenhum exame encontrado para este paciente.");
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarConsultasPorPaciente() {
        System.out.println("\n--- CONSULTAS DE UM PACIENTE ---");
        int id = ConsoleInput.lerInteiro("ID do Paciente: ");

        try {
            pacienteService.buscarPacientePorId(id);

            long total = consultaService.listarConsultas().stream()
                .filter(c -> c.getPaciente().getIdPaciente() == id)
                .peek(c -> System.out.println(" - ID: " + c.getIdConsulta()
                    + " | Data: " + c.getData()
                    + " às " + c.getHoras()
                    + " | Tipo: " + c.getTipo()))
                .count();

            if (total == 0) {
                System.out.println("Nenhuma consulta encontrada para este paciente.");
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarConsultasPorProfissional() {
        System.out.println("\n--- CONSULTAS DE UM PROFISSIONAL ---");
        int id = ConsoleInput.lerInteiro("ID do Profissional: ");

        long total = consultaService.listarConsultas().stream()
            .filter(c -> c.getProfissional().getIdProfissional() == id)
            .peek(c -> System.out.println(" - ID: " + c.getIdConsulta()
                + " | Data: " + c.getData()
                + " às " + c.getHoras()
                + " | Paciente: " + c.getPaciente().getNome()))
            .count();

        if (total == 0) {
            System.out.println("Nenhuma consulta encontrada para este profissional.");
        }
    }

    private static void listarConsultasPorData() {
        System.out.println("\n--- CONSULTAS POR DATA ---");
        String data = ConsoleInput.lerString("Data (dd/mm/aaaa): ");

        long total = consultaService.listarConsultas().stream()
            .filter(c -> c.getData().equals(data))
            .peek(c -> System.out.println(" - ID: " + c.getIdConsulta()
                + " | Hora: " + c.getHoras()
                + " | Paciente: " + c.getPaciente().getNome()
                + " | Profissional: " + c.getProfissional().getNome()))
            .count();

        if (total == 0) {
            System.out.println("Nenhuma consulta encontrada para a data " + data + ".");
        }
    }
}
