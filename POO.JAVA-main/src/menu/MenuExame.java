package menu;

import service.*;
import model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class MenuExame {
   // 1. Instanciamos primeiro o serviço necessário
    private static ConsultaService consultaService = new ConsultaService();
    
    // 2. Passamos esse serviço para o ExameService no construtor
    private static ExameService exameService = new ExameService(consultaService);
    private static PacienteService pacienteService = new PacienteService();

    public static void exibir(BufferedReader br) throws IOException {
        int op = -1;
        do {
            System.out.println("\n--- MENU EXAMES E CONSULTAS ---");
            System.out.println("1. Marcar Exame");
            System.out.println("2. Buscar Exame de um Paciente");
            System.out.println("3. Listar Consultas de um Paciente");
            System.out.println("4. Listar Consultas de um Profissional");
            System.out.println("5. Listar Consultas por Data");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            
            op = Integer.parseInt(br.readLine());
            switch (op) {
                case 1: cadastrarExame(br); break;
                case 2: buscarExamePorPaciente(br); break;
                case 3: listarConsultasPorPaciente(br); break;
                case 4: listarConsultasPorProfissional(br); break;
                case 5: listarConsultasPorData(br); break;
            }
        } while (op != 0);
    }

    private static void cadastrarExame(BufferedReader br) throws IOException {
        System.out.print("ID Exame: "); String id = br.readLine();
        System.out.print("Tipo: "); String tipo = br.readLine();
        System.out.print("Data: "); String data = br.readLine();
        System.out.print("ID Consulta Associada: "); int idCons = Integer.parseInt(br.readLine());
        
        Consulta c = consultaService.buscarConsultaPorId(idCons);
        if (c != null) {
            Exame e = new Exame(tipo, id, data, "Pendente", "N/A", c.getPaciente(), c);
            exameService.solicitarExame(e);
            System.out.println("Exame marcado!");
        }
    }

    private static void buscarExamePorPaciente(BufferedReader br) throws IOException {
        System.out.print("ID do Paciente: "); int idP = Integer.parseInt(br.readLine());
        exameService.listarExames().stream()
            .filter(e -> e.getPaciente().getIdPaciente() == idP)
            .forEach(System.out::println);
    }

    private static void listarConsultasPorPaciente(BufferedReader br) throws IOException {
        System.out.print("ID do Paciente: "); int id = Integer.parseInt(br.readLine());
        consultaService.listarConsultas().stream()
            .filter(c -> c.getPaciente().getIdPaciente() == id)
            .forEach(System.out::println);
    }

    private static void listarConsultasPorProfissional(BufferedReader br) throws IOException {
        System.out.print("ID do Profissional: "); int id = Integer.parseInt(br.readLine());
        consultaService.listarConsultas().stream()
            .filter(c -> c.getProfissional().getIdProfissional() == id)
            .forEach(System.out::println);
    }

    private static void listarConsultasPorData(BufferedReader br) throws IOException {
        System.out.print("Data (dd/mm/aaaa): "); String data = br.readLine();
        consultaService.listarConsultas().stream()
            .filter(c -> c.getData().equals(data))
            .forEach(System.out::println);
    }
}