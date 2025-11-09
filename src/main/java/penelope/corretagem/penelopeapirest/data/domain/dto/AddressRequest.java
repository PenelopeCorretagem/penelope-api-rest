package penelope.corretagem.penelopeapirest.data.domain.dto;

public record AddressRequest(
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
