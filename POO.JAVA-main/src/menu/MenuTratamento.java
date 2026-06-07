package menu;

import service.TratamentoService;
import service.PacienteService;
import service.ProfissionalService;
import model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuTratamento {
    private static TratamentoService service = new TratamentoService();
    private static PacienteService pacienteService = new PacienteService();
    private static ProfissionalService profService = new ProfissionalService();

    public static void exibir(BufferedReader br) throws IOException {
        int op = -1;
        do {
            System.out.println("\n--- MENU TRATAMENTOS ---");
            System.out.println("1. Iniciar Novo Tratamento");
            System.out.println("2. Listar todos os tratamentos");
            System.out.println("3. Ver detalhe de um tratamento");
            System.out.println("4. Listar tratamentos por Paciente");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            
            op = Integer.parseInt(br.readLine());
            switch (op) {
                case 1: iniciarTratamento(br); break;
                case 2: listarTodos(); break;
                case 3: buscarTratamento(br); break;
                case 4: listarPorPaciente(br); break;
            }
        } while (op != 0);
    }

    private static void iniciarTratamento(BufferedReader br) throws IOException {
        System.out.print("Tipo de tratamento: "); String tipo = br.readLine();
        System.out.print("Descrição: "); String desc = br.readLine();
        System.out.print("Data de Início (dd/mm/aaaa): "); String inicio = br.readLine();
        System.out.print("Data de Fim: "); String fim = br.readLine();
        
        System.out.print("ID do Paciente: "); int idPac = Integer.parseInt(br.readLine());
        Paciente p = pacienteService.buscarPacientePorId(idPac);
        
        // Gestão da lista de profissionais (podemos adicionar vários)
        List<Profissional> profissionaisEnvolvidos = new ArrayList<>();
        System.out.print("Quantos profissionais estão envolvidos? ");
        int qtd = Integer.parseInt(br.readLine());
        for (int i = 0; i < qtd; i++) {
            System.out.print("ID do Profissional " + (i + 1) + ": ");
            int idProf = Integer.parseInt(br.readLine());
            Profissional prof = profService.buscarProfissionalPorId(idProf);
            if (prof != null) profissionaisEnvolvidos.add(prof);
        }

        if (p != null) {
            Tratamento t = new Tratamento(tipo, desc, inicio, fim, p, profissionaisEnvolvidos);
            service.iniciarTratamento(t);
            System.out.println("Tratamento registado com sucesso!");
        } else {
            System.out.println("Erro: Paciente não encontrado.");
        }
    }

    private static void listarTodos() {
        service.listarHistoricoTratamentos().forEach(t -> 
            System.out.println("Tipo: " + t.getTipo() + " | Paciente: " + t.getPaciente().getNome()));
    }

    private static void buscarTratamento(BufferedReader br) throws IOException {
        // Assume que o service tem um método de busca por índice ou ID
        System.out.print("Índice do tratamento: ");
        int id = Integer.parseInt(br.readLine());
        Tratamento t = service.buscarPorId(id); 
        
        if (t != null) {
            System.out.println("\n--- DETALHES DO TRATAMENTO ---");
            System.out.println("Tipo: " + t.getTipo());
            System.out.println("Descrição: " + t.getDescricao());
            System.out.println("Paciente: " + t.getPaciente().getNome());
            System.out.println("Profissionais: " + t.getProfissionais().size() + " registrado(s).");
        }
    }

    private static void listarPorPaciente(BufferedReader br) throws IOException {
        System.out.print("ID do Paciente: "); int id = Integer.parseInt(br.readLine());
        service.listarHistoricoTratamentos().stream()
            .filter(t -> t.getPaciente().getIdPaciente() == id)
            .forEach(t -> System.out.println("Tratamento: " + t.getTipo() + " | Início: " + t.getDataInicio()));
    }
}