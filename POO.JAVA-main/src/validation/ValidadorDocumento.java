package validation;

public class ValidadorDocumento implements Validador {

    @Override
    public boolean validar(String valor) {
        // Verifica se o documento é nulo ou vazio
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        String docDoc = valor.trim().toUpperCase();

        boolean ehBI = docDoc.matches("^\\d{12}[A-Z]$");
        boolean ehNUIT = docDoc.matches("^\\d{9}$");

        // Verifica se o documento contém apenas dígitos e tem entre 8 e 15 caracteres
        return ehBI || ehNUIT;
    }
    
}
