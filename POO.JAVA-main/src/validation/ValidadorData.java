package validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidadorData implements Validador {

    private static final String FORMATO_DATA = "dd/MM/yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_DATA);

    /**
     * Valida se a string é uma data válida no formato dd/MM/yyyy
     */
    @Override
    public boolean validar(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(valor, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Valida se a data está no passado ou presente (não pode ser data futura)
     */
    public boolean validarDataNascimento(String data) {
        if (!validar(data)) {
            return false;
        }
        try {
            LocalDate dataNascimento = LocalDate.parse(data, formatter);
            LocalDate hoje = LocalDate.now();
            return dataNascimento.isBefore(hoje) || dataNascimento.isEqual(hoje);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Valida se dataFim é depois de dataInicio
     */
    public boolean validarIntervaloData(String dataInicio, String dataFim) {
        if (!validar(dataInicio) || !validar(dataFim)) {
            return false;
        }
        try {
            LocalDate inicio = LocalDate.parse(dataInicio, formatter);
            LocalDate fim = LocalDate.parse(dataFim, formatter);
            return fim.isAfter(inicio) || fim.isEqual(inicio);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Calcula a idade em anos a partir da data de nascimento
     */
    public int calcularIdade(String dataNascimento) {
        if (!validarDataNascimento(dataNascimento)) {
            throw new IllegalArgumentException("Data de nascimento inválida: " + dataNascimento);
        }
        try {
            LocalDate nascimento = LocalDate.parse(dataNascimento, formatter);
            LocalDate hoje = LocalDate.now();
            return hoje.getYear() - nascimento.getYear() - 
                   (nascimento.getMonthValue() > hoje.getMonthValue() || 
                    (nascimento.getMonthValue() == hoje.getMonthValue() && nascimento.getDayOfMonth() > hoje.getDayOfMonth()) ? 1 : 0);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data de nascimento inválida: " + dataNascimento);
        }
    }

    public static String getFormatoData() {
        return FORMATO_DATA;
    }
}
