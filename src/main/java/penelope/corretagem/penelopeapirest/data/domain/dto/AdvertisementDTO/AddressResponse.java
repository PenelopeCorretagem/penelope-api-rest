package penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO;

import penelope.corretagem.penelopeapirest.core.address.Address;

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
) {
    public static AddressResponse fromDomain(Address address) {
        if (address == null) return null;

        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getUf(),
                address.getRegion(),
                address.getZipCode(),
                address.getComplement()
        );
    }
}
