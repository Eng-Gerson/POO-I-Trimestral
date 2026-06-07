package menu;

import service.ConsultaService;
import service.PacienteService;
import service.ProfissionalService;
import model.Consulta;
import model.Paciente;
import model.Profissional;
import java.io.BufferedReader;
import java.io.IOException;

public class MenuConsulta {
    private static ConsultaService service = new ConsultaService();
    private static PacienteService pacienteService = new PacienteService();
    private static ProfissionalService profService = new ProfissionalService();

    public static void exibir(BufferedReader br) throws IOException {
        int subOpcao = -1;
        do {
            System.out.println("\n--- MENU CONSULTAS ---");
            System.out.println("1. Marcar Nova Consulta");
            System.out.println("2. Procurar Consulta (por ID)");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            
            subOpcao = Integer.parseInt(br.readLine());

            switch (subOpcao) {
                case 1: marcarConsulta(br); break;
                case 2: buscarConsulta(br); break;
            }
        } while (subOpcao != 0);
    }

    private static void marcarConsulta(BufferedReader br) throws IOException {
        System.out.print("ID da consulta: "); int id = Integer.parseInt(br.readLine());
        System.out.print("Data (dd/mm/aaaa): "); String data = br.readLine();
        System.out.print("Hora (hh:mm): "); String hora = br.readLine();
        System.out.print("Tipo (ex: Rotina/Emergência): "); String tipo = br.readLine();
        System.out.print("Observações Clínicas iniciais: "); String obs = br.readLine();
        
        System.out.print("ID do Paciente: "); int idPac = Integer.parseInt(br.readLine());
        Paciente p = pacienteService.buscarPacientePorId(idPac);
        
        System.out.print("ID do Profissional: "); int idProf = Integer.parseInt(br.readLine());
        Profissional prof = profService.buscarProfissionalPorId(idProf);

        if (p != null && prof != null) {
            // Diagnóstico inicial vazio ("A definir")
            Consulta c = new Consulta(id, data, hora, tipo, obs, p, prof, "A definir"); 
            service.agendarConsulta(c);
            System.out.println("Consulta marcada com sucesso!");
        } else {
            System.out.println("Erro: Paciente ou Profissional não encontrado.");
        }
    }

    private static void buscarConsulta(BufferedReader br) throws IOException {
        System.out.print("Digite o ID da consulta: ");
        int id = Integer.parseInt(br.readLine());
        Consulta c = service.buscarConsultaPorId(id);
        
        if (c != null) {
            System.out.println("\n--- DADOS DA CONSULTA ---");
            System.out.println("ID: " + c.getIdConsulta());
            System.out.println("Data/Hora: " + c.getData() + " às " + c.getHoras());
            System.out.println("Tipo: " + c.getTipo());
            System.out.println("Paciente: " + c.getPaciente().getNome());
            System.out.println("Profissional: " + c.getProfissional().getNome());
            System.out.println("Observações: " + c.getObrservasoesClinicas());
            System.out.println("Diagnóstico: " + c.getDiagnostico());
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }
}