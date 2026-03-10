package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserRequest(
        String name,
        String email,
        String password,
        String cpf,
        LocalDate dateBirth,
        BigDecimal monthlyIncome,
        String phone,
        String creci,
        AccessLevel accessLevel
) {
}