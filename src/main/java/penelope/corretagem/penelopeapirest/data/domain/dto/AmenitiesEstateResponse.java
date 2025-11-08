package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AmenitiesEstateResponse(
        String id,
        EstateRequest estate,
        AmenitiesRequest amenity
) {
}
