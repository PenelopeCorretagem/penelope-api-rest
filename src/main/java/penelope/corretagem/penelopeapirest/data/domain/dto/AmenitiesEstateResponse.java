package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AmenitiesEstateResponse(
        Long id,
        EstateRequest estate,
        AmenitiesRequest amenity
) {
}
