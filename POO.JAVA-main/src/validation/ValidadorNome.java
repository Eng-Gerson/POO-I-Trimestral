package validation;

public class ValidadorNome implements Validador {

    @Override
    public boolean validar(String valor) {
        // Verifica se o nome é nulo ou vazio
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        // Verifica se o nome contém apenas letras e espaços
        return valor.matches("[a-zA-ZÀ-ÿ\\s]+");
    }
    
}
