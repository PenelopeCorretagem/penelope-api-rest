package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.AddressRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AddressResponse;
import penelope.corretagem.penelopeapirest.core.address.Address;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

  AddressResponse toResponse(Address address);

  Address toEntity(AddressRequest address);
}
