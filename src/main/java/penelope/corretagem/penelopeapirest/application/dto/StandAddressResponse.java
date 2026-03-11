package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.core.address.Address;

public record StandAddressResponse (
    Long id,
    String street,
    String number,
    String neighborhood,
    String city,
    String uf,
    String region,
    String cep,
    String complement
){
    public static StandAddressResponse fromDomain(Address standAddress){
        if (standAddress == null) return null;

        return new StandAddressResponse(
                standAddress.getId(),
                standAddress.getStreet(),
                standAddress.getNumber(),
                standAddress.getNeighborhood(),
                standAddress.getCity(),
                standAddress.getUf(),
                standAddress.getRegion(),
                standAddress.getZipCode(),
                standAddress.getComplement()
        );
    }
}
