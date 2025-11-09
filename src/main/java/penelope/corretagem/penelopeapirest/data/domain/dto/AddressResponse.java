package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AddressResponse(
        Long id,
        String street,
        String number,
        String neighborhood,
        String city,
        String uf,
        String zipCode,
        String complement
) {
}