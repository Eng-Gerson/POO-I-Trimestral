package menu;

import io.ConsoleInput;
import service.ConsultaService;
import service.PacienteService;
import service.ProfissionalService;
import exception.EntidadeNaoEncontradaException;
import exception.ConsultaInvalidaException;
import model.*;

public class MenuConsulta {
    private static ConsultaService service = new ConsultaService();
    private static PacienteService pacienteService = new PacienteService();
    private static ProfissionalService profService = new ProfissionalService();

    public static void exibir() {
        int op = -1;
        do {
            System.out.println("\n--- MENU CONSULTAS ---");
            System.out.println("1. Marcar Nova Consulta");
            System.out.println("2. Listar todas as Consultas");
            System.out.println("3. Ver dados de uma Consulta (por ID)");
            System.out.println("4. Cancelar Consulta");
            System.out.println("0. Voltar");

            op = ConsoleInput.lerInteiro("Opção: ");

            switch (op) {
                case 1: marcarConsulta(); break;
                case 2: listarTodas(); break;
                case 3: buscarConsulta(); break;
                case 4: cancelarConsulta(); break;
                case 0: System.out.println("A voltar ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void marcarConsulta() {
        System.out.println("\n--- MARCAR NOVA CONSULTA ---");
        try {
            String data = ConsoleInput.lerString("Data (dd/mm/aaaa): ");
            String hora = ConsoleInput.lerString("Hora (hh:mm): ");
            String tipo = ConsoleInput.lerString("Tipo (ex: Rotina/Emergência): ");
            String obs  = ConsoleInput.lerString("Observações Clínicas iniciais: ");

            int idPac   = ConsoleInput.lerInteiro("ID do Paciente: ");
            Paciente p  = pacienteService.buscarPacientePorId(idPac);

            int idProf        = ConsoleInput.lerInteiro("ID do Profissional: ");
            Profissional prof = profService.buscarProfissionalPorId(idProf);

            Consulta c = new Consulta(data, hora, tipo, obs, p, prof, "A definir");
            service.agendarConsulta(c);
            System.out.println("Sucesso: Consulta marcada com sucesso! ID atribuído: " + c.getIdConsulta());

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO DE REGISTO]: " + e.getMessage());
        } catch (ConsultaInvalidaException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarTodas() {
        System.out.println("\n--- LISTA DE CONSULTAS ---");
        java.util.List<Consulta> lista = service.listarConsultas();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma consulta registada até ao momento.");
            return;
        }

        lista.forEach(c ->
            System.out.println("ID: " + c.getIdConsulta()
                + " | Data: " + c.getData()
                + " às " + c.getHoras()
                + " | Paciente: " + c.getPaciente().getNome()
                + " | Profissional: " + c.getProfissional().getNome()));
    }

    private static void buscarConsulta() {
        System.out.println("\n--- BUSCAR CONSULTA ---");
        int id = ConsoleInput.lerInteiro("ID da consulta: ");

        try {
            Consulta c = service.buscarConsultaPorId(id);

            System.out.println("\n--- DADOS DA CONSULTA ---");
            System.out.println("ID: " + c.getIdConsulta());
            System.out.println("Data/Hora: " + c.getData() + " às " + c.getHoras());
            System.out.println("Tipo: " + c.getTipo());
            System.out.println("Paciente: " + c.getPaciente().getNome());
            System.out.println("Profissional: " + c.getProfissional().getNome());
            System.out.println("Observações: " + c.getObrservasoesClinicas());
            System.out.println("Diagnóstico: " + c.getDiagnostico());

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void cancelarConsulta() {
        System.out.println("\n--- CANCELAR CONSULTA ---");
        int id = ConsoleInput.lerInteiro("ID da consulta a cancelar: ");

        try {
            service.cancelarConsulta(id);
            System.out.println("Sucesso: Consulta ID " + id + " cancelada com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }
}
