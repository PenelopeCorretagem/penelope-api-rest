package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstateId;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.estate.ImageEstateType;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AmenitiesRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateRequest;
import penelope.corretagem.penelopeapirest.application.dto.EstateResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EstateMapper {

  // --- Entity → Response ---
  EstateResponse toResponse(Estate estate);

  default String map(ImageEstateType type) {
    return type != null ? type.getDescription() : null;
  }

  default AmenitiesResponse map(AmenitiesEstate entity) {
    if (entity == null || entity.getAmenity() == null) return null;
    return new AmenitiesResponse(
            entity.getAmenity().getId(),
            entity.getAmenity().getDescription()
    );
  }

  default Set<AmenitiesResponse> mapAmenities(Set<AmenitiesEstate> amenities) {
    if (amenities == null) return null;
    return amenities.stream().map(this::map).collect(Collectors.toSet());
  }

  // --- Request → Entity ---
  Estate toEntity(EstateRequest estate);

  void updateEntityFromRequest(EstateRequest estateRequest, @MappingTarget Estate estate);

  // Converte AmenitiesRequest → AmenitiesEstateEntity
  default AmenitiesEstate map(AmenitiesRequest request) {
    if (request == null) return null;
    AmenitiesEstate entity = new AmenitiesEstate();
    AmenitiesEstateId pk = new AmenitiesEstateId();
    pk.setId(request.id());
    entity.setId(pk);
    return entity;
  }

  // Converte Set<AmenitiesRequest> → Set<AmenitiesEstateEntity>
  default Set<AmenitiesEstate> mapAmenitiesRequestSet(Set<AmenitiesRequest> requests) {
    if (requests == null) return null;
    return requests.stream()
            .map(this::map)
            .collect(Collectors.toSet());
  }

  // Atualiza amenities no updateEntityFromRequest
  default void updateAmenitiesFromRequest(EstateRequest request, Estate entity) {
    if (request.amenities() != null) {
      entity.setAmenities(mapAmenitiesRequestSet(request.amenities()));
    }
  }
}

