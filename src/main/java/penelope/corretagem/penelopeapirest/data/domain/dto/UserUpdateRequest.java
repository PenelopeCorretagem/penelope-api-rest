package penelope.corretagem.penelopeapirest.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserUpdateRequest(
        @JsonProperty("nomeCompleto") String name,
        String email,
        @JsonProperty("senha") String password,
        String cpf,
        @JsonProperty("dtNascimento") LocalDate dateBirth,
        @JsonProperty("rendaMensal") BigDecimal monthlyIncome
) {
}