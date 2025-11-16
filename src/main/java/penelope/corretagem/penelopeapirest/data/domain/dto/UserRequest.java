package penelope.corretagem.penelopeapirest.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record UserRequest(
        @JsonProperty("nomeCompleto") String name,
        String email,
        @JsonProperty("senha") String password,
        String cpf,
        @JsonProperty("dtNascimento") LocalDate dateBirth,
        @JsonProperty("rendaMensal") BigDecimal monthlyIncome,
        String phone,
        String creci,
        AccessLevel accessLevel,
        @JsonProperty("dataCriacao") LocalDate dateCreation,
        @JsonProperty("ativo") boolean active,
        Set<AppointmentEntity> appointmentsClient,
        Set<AppointmentEntity> appointmentsEstateAgent
) {
}
