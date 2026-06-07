package menu;

import java.io.BufferedReader;
import java.io.IOException;

public class MenuPrincipal {

    public static void exibir(BufferedReader br) {
        int opcao = -1;
        do {
            System.out.println("\n====================================");
            System.out.println("      SISTEMA DE GESTÃO HCM - V2.0   ");
            System.out.println("====================================");
            System.out.println("--- GESTÃO DE RECURSOS ---");
            System.out.println("1. Pacientes");
            System.out.println("2. Profissionais");
            System.out.println("--- GESTÃO CLÍNICA ---");
            System.out.println("4. Consultas");
            System.out.println("5. Exames");
            System.out.println("6. Internamentos");
            System.out.println("7. Tratamentos");
            System.out.println("------------------------------------");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma área de gestão: ");

            try {
                opcao = Integer.parseInt(br.readLine());

                switch (opcao) {
                    case 1: MenuPaciente.exibir(br); break;
                    case 2: MenuProfissional.exibir(br); break;
                    case 3: MenuConsulta.exibir(br); break;
                    case 4: MenuExame.exibir(br); break;
                    case 5: MenuInternamento.exibir(br); break;
                    case 6: MenuTratamento.exibir(br); break;
                    case 0: System.out.println("Encerrando o sistema..."); break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Erro: Entrada inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
}