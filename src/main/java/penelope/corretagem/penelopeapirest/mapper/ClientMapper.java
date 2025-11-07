package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.ClientRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.ClientResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.ClientEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

  ClientEntity toEntity(ClientRequest request);

  ClientResponse toResponse(ClientEntity entity);

  ClientEntity updateEntityFromRequest(ClientRequest request, ClientEntity entity);
}
