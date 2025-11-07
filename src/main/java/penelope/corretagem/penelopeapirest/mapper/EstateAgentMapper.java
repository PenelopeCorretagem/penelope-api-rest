package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateAgentRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateAgentResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateAgentEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EstateAgentMapper {

  EstateAgentResponse toResponse(EstateAgentEntity estateAgent);

  EstateAgentEntity toEntity(EstateAgentRequest estateAgentResponse);

  void updateEntityFromRequest(EstateAgentRequest estateAgentRequest, @MappingTarget EstateAgentEntity estateAgentEntity);
}
