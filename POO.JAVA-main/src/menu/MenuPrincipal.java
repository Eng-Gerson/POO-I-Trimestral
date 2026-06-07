package menu;

import io.ConsoleInput; // Importando o teu leitor utilitário

public class MenuPrincipal {

    public static void exibir() {
        int opcao = -1;
        do {
            System.out.println("\n====================================");
            System.out.println("     SISTEMA DE GESTAO HCM    ");
            System.out.println("====================================");
            System.out.println("1. Pacientes");
            System.out.println("2. Profissionais");
            System.out.println("3. Consultas");
            System.out.println("4. Exames");
            System.out.println("5. Internamentos");
            System.out.println("6. Tratamentos");
            System.out.println("------------------------------------");
            System.out.println("0. Sair do Sistema");
            
            // Usando a nova classe utilitária de leitura
            opcao = ConsoleInput.lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1: MenuPaciente.exibir(); break;
                case 2: MenuProfissional.exibir(); break;
                case 3: MenuConsulta.exibir(); break;
                case 4: MenuExame.exibir(); break;
                case 5: MenuInternamento.exibir(); break;
                case 6: MenuTratamento.exibir(); break;
                case 0: System.out.println("Encerrando o sistema..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}