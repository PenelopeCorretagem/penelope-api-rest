package penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO;

import penelope.corretagem.penelopeapirest.core.amenities.Amenities;

public record AmenitiesResponse(
        Long id,
        String description
) {
    public static AmenitiesResponse fromDomain(Amenities amenities) {
        return new AmenitiesResponse(
                amenities.getId(),
                amenities.getDescription()
                );
    }
}
