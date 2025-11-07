package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public interface EstateMapper {

  EstateResponse toResponse(EstateEntity estate);

  EstateEntity toEntity(EstateRequest estate);

  void updateEntityFromRequest(EstateRequest estateRequest, @MappingTarget EstateEntity estateEntity);
}
