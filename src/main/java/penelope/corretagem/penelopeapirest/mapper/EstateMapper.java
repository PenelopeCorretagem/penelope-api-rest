package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.AmenitiesEstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.ImageEstateTypeEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public interface EstateMapper {

  EstateResponse toResponse(EstateEntity estate);

  EstateEntity toEntity(EstateRequest estate);

  void updateEntityFromRequest(EstateRequest estateRequest, @MappingTarget EstateEntity estateEntity);

  default String map(ImageEstateTypeEntity type) {
    return type != null ? type.getDescription() : null;
  }

  // Método auxiliar para mapear um AmenitiesEstateEntity para AmenitiesResponse
  default AmenitiesResponse map(AmenitiesEstateEntity entity) {
    return new AmenitiesResponse(
            entity.getAmenity().getId(),           // pega o id do diferencial
            entity.getAmenity().getDescription()   // pega a descrição
    );
  }

  // Método auxiliar para mapear um conjunto de AmenitiesEstateEntity para um Set de AmenitiesResponse
  default Set<AmenitiesResponse> mapAmenities(Set<AmenitiesEstateEntity> amenities) {
    return amenities.stream().map(this::map).collect(Collectors.toSet());
  }
}
