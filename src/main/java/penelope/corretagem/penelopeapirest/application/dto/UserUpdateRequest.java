package penelope.corretagem.penelopeapirest.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserUpdateRequest(
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