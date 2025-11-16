package penelope.corretagem.penelopeapirest.util;

public class StringUtils {

    /**
     * Normaliza uma string para comparação com o banco:
     * - converte para minúsculas
     * - substitui hífens por espaços
     * - remove espaços extras
     */
    public static String normalize(String value) {
        if (value == null) return null;
        return value.trim().replace("-", " ").toLowerCase();
    }
}
