package io;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ConsoleInput {
    // Mantemos uma única instância do BufferedReader para toda a aplicação
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // 1. Método para ler Texto (String)
    public static String lerString(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String input = reader.readLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("[Erro] O campo não pode estar vazio. Tente novamente.");
                    continue;
                }
                return input.trim();
            } catch (IOException e) {
                System.out.println("[Erro] Problema ao ler o teclado. Tente novamente.");
            }
        }
    }

    // 2. Método para ler Números Inteiros (int) com validação de formato
    public static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("[Erro de Formato] Introduza um número inteiro válido.");
            } catch (IOException e) {
                System.out.println("[Erro] Problema ao ler o teclado.");
            }
        }
    }

    // 3. Método para ler Números Decimais (double) - útil para preços, taxas, etc.
    public static double lerDecimal(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("[Erro de Formato] Introduza um número decimal válido (Ex: 12.5).");
            } catch (IOException e) {
                System.out.println("[Erro] Problema ao ler o teclado.");
            }
        }
    }
}