package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserRequest(
        String name,
        String email,
        String password,
        String phone,
        String cpf,
        LocalDate dateBirth,
        BigDecimal monthlyIncome,
        String creci,
        AccessLevel accessLevel
) {
}