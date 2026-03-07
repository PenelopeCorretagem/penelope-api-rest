package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AddressResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.ImagesResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.StandAddressResponse;

import java.util.Set;
import java.util.stream.Collectors; // Necessário para mapear as listas/sets

public record EstateResponse(
        Long id,
        String title,
        String description,
        Double area,
        Integer numberOfRooms,
        String type,
        AddressResponse address,
        StandAddressResponse addressStand,
        Set<ImagesResponse> images,
        Set<AmenitiesResponse> amenities
) {
    public static EstateResponse fromDomain(Estate estate) {
        if (estate == null) return null;

        return new EstateResponse(
                estate.getId(),
                estate.getTitle(),
                estate.getDescription(),
                estate.getArea(),
                estate.getNumberOfRooms(),
                estate.getType() != null ? estate.getType().name() : null,
                AddressResponse.fromDomain(estate.getAddress()),
                StandAddressResponse.fromDomain(estate.getStandAddress()),
                estate.getImages() != null ?
                        estate.getImages().stream()
                                .map(ImagesResponse::fromDomain)
                                .collect(Collectors.toSet())
                        : null,
                estate.getAmenities() != null ?
                        estate.getAmenities().stream()
                                .map(amenitiesEstate -> AmenitiesResponse.fromDomain(amenitiesEstate.getAmenity()))
                                .collect(Collectors.toSet())
                        : null
        );
    }
}