package penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO;

public record AddressResponse(
    Long id,
    String street,
    String number,
    String neighborhood,
    String city,
    String uf,
    String region,
    String cep,
    String complement
) {}
