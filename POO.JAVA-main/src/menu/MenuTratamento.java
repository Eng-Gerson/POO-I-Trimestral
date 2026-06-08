package menu;

import io.ConsoleInput;
import service.TratamentoService;
import service.PacienteService;
import service.ProfissionalService;
import exception.EntidadeNaoEncontradaException;
import exception.TratamentoInvalidoException;
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
            System.out.println("5. Encerrar Tratamento");
            System.out.println("6. Marcar Tratamento como Concluído");
            System.out.println("0. Voltar");
            
            op = ConsoleInput.lerInteiro("Opção: ");
            
            switch (op) {
                case 1: iniciarTratamento(); break;
                case 2: listarTodos(); break;
                case 3: buscarTratamento(); break;
                case 4: listarPorPaciente(); break;
                case 5: encerrarTratamento(); break;
                case 6: concluirTratamento(); break;
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
            String inicio = ConsoleInput.lerString("Data de Início (dd/MM/yyyy): ");
            String fim = ConsoleInput.lerString("Data de Fim (dd/MM/yyyy) [deixe vazio se ainda não sabe]: ");
            
            int idPac = ConsoleInput.lerInteiro("ID do Paciente: ");
            Paciente p = pacienteService.buscarPacientePorId(idPac);
            
            // NOVO: Gestão obrigatória da lista de profissionais
            List<Profissional> profissionaisEnvolvidos = new ArrayList<>();
            int qtd = ConsoleInput.lerInteiro("Quantos profissionais estão envolvidos? ");
            
            if (qtd <= 0) {
                System.out.println("[ERRO]: O tratamento deve ter pelo menos um profissional!");
                return;
            }
            
            for (int i = 0; i < qtd; i++) {
                int idProf = ConsoleInput.lerInteiro("ID do Profissional " + (i + 1) + ": ");
                try {
                    Profissional prof = profService.buscarProfissionalPorId(idProf);
                    if (prof != null) {
                        profissionaisEnvolvidos.add(prof);
                    }
                } catch (EntidadeNaoEncontradaException e) {
                    System.out.println("[Aviso]: " + e.getMessage() + " Não será adicionado ao tratamento.");
                }
            }

            // Criação e envio para o serviço
            Tratamento t = new Tratamento(id, tipo, desc, inicio, fim.isEmpty() ? null : fim, p, profissionaisEnvolvidos);
            service.iniciarTratamento(t);

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO DE REGISTO]: " + e.getMessage());
        } catch (TratamentoInvalidoException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
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
            System.out.println("ID: " + t.getIdTratamento() + " | Tipo: " + t.getTipo() 
                + " | Paciente: " + t.getPaciente().getNome() 
                + " | Status: " + t.getStatus()));
    }

    private static void buscarTratamento() {
        System.out.println("\n--- BUSCAR TRATAMENTO ---");
        int id = ConsoleInput.lerInteiro("Índice/ID do tratamento: ");
        
        try {
            Tratamento t = service.buscarPorId(id); 
            
            System.out.println("\n--- DETALHES DO TRATAMENTO ---");
            System.out.println("ID: " + t.getIdTratamento());
            System.out.println("Tipo: " + t.getTipo());
            System.out.println("Descrição: " + t.getDescricao());
            System.out.println("Data de Início: " + t.getDataInicio());
            System.out.println("Data de Fim: " + (t.getDataFim() != null ? t.getDataFim() : "Não definida"));
            System.out.println("Paciente: " + t.getPaciente().getNome());
            System.out.println("Profissionais: " + t.getProfissionais().size() + " registado(s).");
            System.out.println("Status: " + t.getStatus());
            System.out.println("Concluído: " + (t.isConcluido() ? "Sim" : "Não"));
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void listarPorPaciente() {
        System.out.println("\n--- LISTAR TRATAMENTOS POR PACIENTE ---");
        int id = ConsoleInput.lerInteiro("ID do Paciente: ");
        
        try {
            pacienteService.buscarPacientePorId(id);
            
            System.out.println("Resultados para o Paciente ID " + id + ":");
            long total = service.listarHistoricoTratamentos().stream()
                .filter(t -> t.getPaciente().getIdPaciente() == id)
                .peek(t -> System.out.println(" - ID: " + t.getIdTratamento() + " | Tratamento: " + t.getTipo() 
                    + " | Status: " + t.getStatus()))
                .count();
                
            if (total == 0) {
                System.out.println("Nenhum histórico de tratamento associado a este paciente.");
            }
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    /**
     * NOVO: Opção para encerrar um tratamento
     */
    private static void encerrarTratamento() {
        System.out.println("\n--- ENCERRAR TRATAMENTO ---");
        int id = ConsoleInput.lerInteiro("ID do tratamento a encerrar: ");
        String dataFim = ConsoleInput.lerString("Data de Término (dd/MM/yyyy): ");
        
        try {
            service.encerraTratamento(id, dataFim);
        } catch (TratamentoInvalidoException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    /**
     * NOVO: Opção para marcar um tratamento como concluído
     */
    private static void concluirTratamento() {
        System.out.println("\n--- MARCAR TRATAMENTO COMO CONCLUÍDO ---");
        int id = ConsoleInput.lerInteiro("ID do tratamento a concluir: ");
        
        try {
            service.concluirTratamento(id);
        } catch (TratamentoInvalidoException e) {
            System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }
}
