package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AmenitiesEstateRequest(
        Long id,
        EstateRequest estate,
        AmenitiesRequest amenity
) {
}
