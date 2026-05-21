package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.core.estate.Estate;

import java.util.Set;
import java.util.stream.Collectors; 

public record EstateResponse(
        Long id,
        String title,
        String description,
        Double area,
        Integer numberOfRooms,
        String type,
        AddressResponse address,
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
                estate.getType() != null ? estate.getType().getDisplayName() : null,
                AddressResponse.fromDomain(estate.getAddress()),
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