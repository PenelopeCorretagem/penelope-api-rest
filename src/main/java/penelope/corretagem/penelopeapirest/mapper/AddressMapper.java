package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.AddressRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AddressResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.AddressEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

  AddressResponse toResponse(AddressEntity address);

  AddressEntity toEntity(AddressRequest address);
}
