package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        String email,
        String cpf,
        LocalDate dateBirth,
        BigDecimal monthlyIncome,
        String phone,
        String creci,
        AccessLevel accessLevel,
        boolean active
) {
}