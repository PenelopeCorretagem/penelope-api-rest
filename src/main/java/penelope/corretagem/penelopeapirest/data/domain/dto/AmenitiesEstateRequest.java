package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AmenitiesEstateRequest(
        String id,
        EstateRequest estate,
        AmenitiesRequest amenity
) {
}
