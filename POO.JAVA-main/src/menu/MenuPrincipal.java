package menu;

import io.ConsoleInput;

public class MenuPrincipal {

    public static void exibir() {
        int opcao = -1;
        do {
            exibirMenu();
            opcao = ConsoleInput.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    MenuPaciente.exibir();
                    break;
                case 2:
                    MenuProfissional.exibir();
                    break;
                case 3:
                    MenuConsulta.exibir();
                    break;
                case 4:
                    MenuExame.exibir();
                    break;
                case 5:
                    MenuTratamento.exibir();
                    break;
                case 6:
                    MenuInternamento.exibir();
                    break;
                case 7:
                    MenuDepartamento.exibir();
                    break;
                case 0:
                    System.out.println("\n=== SAINDO DO PROGRAMA ===");
                    System.out.println("Até logo!");
                    break;
                default:
                    System.out.println("\n[ERRO]: Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         SISTEMA DE GESTÃO HOSPITALAR - MENU PRINCIPAL  ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Gestão de Pacientes                                ║");
        System.out.println("║  2. Gestão de Profissionais                            ║");
        System.out.println("║  3. Gestão de Consultas                                ║");
        System.out.println("║  4. Gestão de Exames                                   ║");
        System.out.println("║  5. Gestão de Tratamentos                              ║");
        System.out.println("║  6. Gestão de Internamentos                            ║");
        System.out.println("║  7. Gestão de Departamentos                            ║");
        System.out.println("║  0. Sair do Programa                                   ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
}
