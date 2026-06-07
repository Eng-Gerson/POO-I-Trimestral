package menu;

import io.ConsoleInput;
import service.TratamentoService;
import service.PacienteService;
import service.ProfissionalService;
import exception.EntidadeNaoEncontradaException;
import exception.PacienteInvalidoException;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class MenuTratamento {
    private static TratamentoService service = new TratamentoService();
    private static PacienteService pacienteService = new PacienteService();
    private static ProfissionalService profService = new ProfissionalService();

    public static void exibir() {
        int op = -1;
        do {
            System.out.println("\n--- MENU TRATAMENTOS ---");
            System.out.println("1. Iniciar Novo Tratamento");
            System.out.println("2. Listar todos os tratamentos");
            System.out.println("3. Ver detalhe de um tratamento");
            System.out.println("4. Listar tratamentos por Paciente");
            System.out.println("0. Voltar");
            
            op = ConsoleInput.lerInteiro("Opção: ");
            
            switch (op) {
                case 1: iniciarTratamento(); break;
                case 2: listarTodos(); break;
                case 3: buscarTratamento(); break;
                case 4: listarPorPaciente(); break;
                case 0: System.out.println("A voltar ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void iniciarTratamento() {
        System.out.println("\n--- INICIAR NOVO TRATAMENTO ---");
        
        try {
            int id = ConsoleInput.lerInteiro("ID do tratamento: ");
            String tipo = ConsoleInput.lerString("Tipo de tratamento: ");
            String desc = ConsoleInput.lerString("Descrição: ");
            String inicio = ConsoleInput.lerString("Data de Início (dd/mm/aaaa): ");
            String fim = ConsoleInput.lerString("Data de Fim (dd/mm/aaaa): ");
            
            int idPac = ConsoleInput.lerInteiro("ID do Paciente: ");
            
            // O teu service agora lança EntidadeNaoEncontradaException se o paciente não existir!
            Paciente p = pacienteService.buscarPacientePorId(idPac);
            
            // Gestão da lista de profissionais
            List<Profissional> profissionaisEnvolvidos = new ArrayList<>();
            int qtd = ConsoleInput.lerInteiro("Quantos profissionais estão envolvidos? ");
            
            for (int i = 0; i < qtd; i++) {
                int idProf = ConsoleInput.lerInteiro("ID do Profissional " + (i + 1) + ": ");
                try {
                    // Assume-se que o profService também lançará exceção ou retornará null
                    Profissional prof = profService.buscarProfissionalPorId(idProf);
                    if (prof != null) {
                        profissionaisEnvolvidos.add(prof);
                    }
                } catch (EntidadeNaoEncontradaException e) {
                    System.out.println("[Aviso]: " + e.getMessage() + " Não será adicionado ao tratamento.");
                }
            }

            // Criação e envio para o serviço
            Tratamento t = new Tratamento(id,tipo, desc, inicio, fim, p, profissionaisEnvolvidos);
            service.iniciarTratamento(t);
            System.out.println("Sucesso: Tratamento registado com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            // Captura falha caso o paciente principal não exista no sistema
            System.out.println("\n[ERRO DE REGISTO]: " + e.getMessage());
        } catch (RuntimeException e) {
            // Captura qualquer outra validação de negócio que falte (ex: datas incorretas vindo do service)
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        }
    }

    private static void listarTodos() {
        System.out.println("\n--- LISTA DE TODOS OS TRATAMENTOS ---");
        List<Tratamento> lista = service.listarHistoricoTratamentos();
        
        if (lista.isEmpty()) {
            System.out.println("Nenhum tratamento registado até ao momento.");
            return;
        }

        lista.forEach(t -> 
            System.out.println("Tipo: " + t.getTipo() + " | Paciente: " + t.getPaciente().getNome()));
    }

    private static void buscarTratamento() {
        System.out.println("\n--- BUSCAR TRATAMENTO ---");
        int id = ConsoleInput.lerInteiro("Índice/ID do tratamento: ");
        
        try {
            Tratamento t = service.buscarPorId(id); 
            
            if (t == null) {
                throw new EntidadeNaoEncontradaException("Tratamento com o índice " + id + " não existe.");
            }
            
            System.out.println("\n--- DETALHES DO TRATAMENTO ---");
            System.out.println("Tipo: " + t.getTipo());
            System.out.println("Descrição: " + t.getDescricao());
            System.out.println("Data de Início: " + t.getDataInicio());
            System.out.println("Data de Fim: " + t.getDataFim());
            System.out.println("Paciente: " + t.getPaciente().getNome());
            System.out.println("Profissionais: " + t.getProfissionais().size() + " registrado(s).");
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarPorPaciente() {
        System.out.println("\n--- LISTAR TRATAMENTOS POR PACIENTE ---");
        int id = ConsoleInput.lerInteiro("ID do Paciente: ");
        
        try {
            // Primeiro certifica-te de que o paciente existe no sistema
            pacienteService.buscarPacientePorId(id);
            
            System.out.println("Resultados para o Paciente ID " + id + ":");
            long total = service.listarHistoricoTratamentos().stream()
                .filter(t -> t.getPaciente().getIdPaciente() == id)
                .peek(t -> System.out.println(" - Tratamento: " + t.getTipo() + " | Início: " + t.getDataInicio()))
                .count();
                
            if (total == 0) {
                System.out.println("Nenhum histórico de tratamento associado a este paciente.");
            }
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }
}