package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record UserResponse(
        String name,
        String email,
        String password,
        String cpf,
        LocalDate dateBirth,
        BigDecimal monthlyIncome,
        String phone,
        String creci,
        AccessLevel accessLevel,
        LocalDate dateCreation,
        boolean active,
        Set<AppointmentEntity> appointmentsClient,
        Set<AppointmentEntity> appointmentsEstateAgent
) {
}