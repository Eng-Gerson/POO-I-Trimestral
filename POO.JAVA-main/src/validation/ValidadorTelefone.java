package validation;

public class ValidadorTelefone implements Validador {

    @Override
    public boolean validar(String valor) {
        // Verifica se o telefone é nulo ou vazio
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        // Verifica se o telefone contém apenas dígitos e tem entre 8 e 15 caracteres
        return valor.trim().matches("^8[2-7]\\d{7}$");
    }
    
}
