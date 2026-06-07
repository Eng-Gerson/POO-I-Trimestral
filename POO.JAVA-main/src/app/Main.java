package app;

import menu.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        // O Main apenas invoca a classe de menu
        MenuPrincipal.exibir(new java.io.BufferedReader(new java.io.InputStreamReader(System.in)));
    }
}