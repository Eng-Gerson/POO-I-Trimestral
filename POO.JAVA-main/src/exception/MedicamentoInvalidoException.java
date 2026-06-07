package exception;

public class MedicamentoInvalidoException extends RuntimeException {
    public MedicamentoInvalidoException(String message) {
        super(message);
    }
}
