package penelope.corretagem.penelopeapirest.application.dto;

public record AddressRequest(
        Long id,
        String street,
        String number,
        String neighborhood,
        String city,
        String uf,
        String zipCode,
        String complement,
        String region
) {
}
