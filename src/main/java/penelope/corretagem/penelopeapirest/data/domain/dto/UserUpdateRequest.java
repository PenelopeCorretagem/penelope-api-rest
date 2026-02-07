package penelope.corretagem.penelopeapirest.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
        String phone
) {
}