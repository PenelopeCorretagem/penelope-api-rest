package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.AppointmentRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AppointmentResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {

  AppointmentResponse toResponse(AppointmentEntity entity);

  AppointmentEntity toEntity(AppointmentRequest request);
}