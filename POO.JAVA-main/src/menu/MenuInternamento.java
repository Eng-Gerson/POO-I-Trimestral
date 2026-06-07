package menu;

import service.InternamentoService;
import service.PacienteService;
import service.DepartamentoService;
import model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MenuInternamento {
    private static InternamentoService service = new InternamentoService();
    private static PacienteService pacienteService = new PacienteService();
    private static DepartamentoService deptService = new DepartamentoService();

    public static void exibir(BufferedReader br) throws IOException {
        int op = -1;
        do {
            System.out.println("\n--- MENU INTERNAMENTOS ---");
            System.out.println("1. Registar Novo Internamento");
            System.out.println("2. Listar todos os internamentos");
            System.out.println("3. Ver dados de um internamento");
            System.out.println("4. Listar internamentos de um Paciente");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            
            op = Integer.parseInt(br.readLine());
            switch (op) {
                case 1: registarInternamento(br); break;
                case 2: listarTodos(); break;
                case 3: buscarInternamento(br); break;
                case 4: listarPorPaciente(br); break;
            }
        } while (op != 0);
    }

    private static void registarInternamento(BufferedReader br) throws IOException {
        System.out.print("Data de Entrada (dd/mm/aaaa): "); String entrada = br.readLine();
        System.out.print("Data de Saída (ou 'Pendente'): "); String saida = br.readLine();
        System.out.print("Motivo: "); String motivo = br.readLine();
        
        System.out.print("ID do Paciente: "); int idPac = Integer.parseInt(br.readLine());
        Paciente p = pacienteService.buscarPacientePorId(idPac);
        
        System.out.print("ID do Departamento: "); int idDept = Integer.parseInt(br.readLine());
        Departamento d = deptService.buscarDepartamentoPorId(idDept);

        if (p != null && d != null) {
            Internamento i = new Internamento(entrada, saida, motivo, d, p);
            // Assumindo que precisas de inicializar a lista de profissionais
            // i.setProfissionaisEnvolvidos(new ArrayList<>()); 
            service.registrarInternamento(i);
            System.out.println("Internamento registado com sucesso!");
        } else {
            System.out.println("Erro: Paciente ou Departamento não encontrado.");
        }
    }

    private static void listarTodos() {
        service.listarHistorico().forEach(i -> 
            System.out.println("Paciente: " + i.getPaciente().getNome() + " | Motivo: " + i.getMotivo()));
    }

    private static void buscarInternamento(BufferedReader br) throws IOException {
        System.out.print("ID Internamento (ou índice): "); 
        int id = Integer.parseInt(br.readLine());
        // Ajusta conforme o teu método de busca no service
        Internamento i = service.buscarPorId(id); 
        
        if (i != null) {
            System.out.println("\n--- DETALHES DO INTERNAMENTO ---");
            System.out.println("Paciente: " + i.getPaciente().getNome());
            System.out.println("Departamento: " + i.getDepartamento().getNomeDepartamento());
            System.out.println("Entrada: " + i.getDataEntrada() + " | Saída: " + i.getDataSaida());
            System.out.println("Motivo: " + i.getMotivo());
        }
    }

    private static void listarPorPaciente(BufferedReader br) throws IOException {
        System.out.print("ID do Paciente: "); int id = Integer.parseInt(br.readLine());
        service.listarHistorico().stream()
            .filter(i -> i.getPaciente().getIdPaciente() == id)
            .forEach(i -> System.out.println("Data: " + i.getDataEntrada() + " | Motivo: " + i.getMotivo()));
    }
}