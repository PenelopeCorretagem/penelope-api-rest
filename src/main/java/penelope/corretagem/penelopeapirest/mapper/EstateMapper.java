package penelope.corretagem.penelopeapirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AmenitiesRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.AmenitiesEstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.AmenitiesEstateId;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.ImageEstateTypeEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EstateMapper {

  // --- Entity → Response ---
  EstateResponse toResponse(EstateEntity estate);

  default String map(ImageEstateTypeEntity type) {
    return type != null ? type.getDescription() : null;
  }

  default AmenitiesResponse map(AmenitiesEstateEntity entity) {
    if (entity == null || entity.getAmenity() == null) return null;
    return new AmenitiesResponse(
            entity.getAmenity().getId(),
            entity.getAmenity().getDescription()
    );
  }

  default Set<AmenitiesResponse> mapAmenities(Set<AmenitiesEstateEntity> amenities) {
    if (amenities == null) return null;
    return amenities.stream().map(this::map).collect(Collectors.toSet());
  }

  // --- Request → Entity ---
  EstateEntity toEntity(EstateRequest estate);

  void updateEntityFromRequest(EstateRequest estateRequest, @MappingTarget EstateEntity estateEntity);

  // Converte AmenitiesRequest → AmenitiesEstateEntity
  default AmenitiesEstateEntity map(AmenitiesRequest request) {
    if (request == null) return null;
    AmenitiesEstateEntity entity = new AmenitiesEstateEntity();
    AmenitiesEstateId pk = new AmenitiesEstateId();
    pk.setId(request.id());
    entity.setId(pk);
    return entity;
  }

  // Converte Set<AmenitiesRequest> → Set<AmenitiesEstateEntity>
  default Set<AmenitiesEstateEntity> mapAmenitiesRequestSet(Set<AmenitiesRequest> requests) {
    if (requests == null) return null;
    return requests.stream()
            .map(this::map)
            .collect(Collectors.toSet());
  }

  // Atualiza amenities no updateEntityFromRequest
  default void updateAmenitiesFromRequest(EstateRequest request, EstateEntity entity) {
    if (request.amenities() != null) {
      entity.setAmenities(mapAmenitiesRequestSet(request.amenities()));
    }
  }
}

