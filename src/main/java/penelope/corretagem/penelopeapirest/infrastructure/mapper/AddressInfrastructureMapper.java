package penelope.corretagem.penelopeapirest.infrastructure.mapper;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.address.Address;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AddressJpaEntity;

@Component
public class AddressInfrastructureMapper {

    public Address toDomain(AddressJpaEntity jpaAddress) {
        if (jpaAddress == null) return null;

        return Address.restore(
                jpaAddress.getId(),
                jpaAddress.getStreet(),
                jpaAddress.getNumber(),
                jpaAddress.getNeighborhood(),
                jpaAddress.getCity(),
                jpaAddress.getUf(),
                jpaAddress.getZipCode(),
                jpaAddress.getComplement(),
                jpaAddress.getRegion()
        );
    }

    public AddressJpaEntity toEntity(Address domain) {
        if (domain == null) return null;

        var entity = new AddressJpaEntity();
        entity.setId(domain.getId());
        entity.setStreet(domain.getStreet());
        entity.setNumber(domain.getNumber());
        entity.setNeighborhood(domain.getNeighborhood());
        entity.setCity(domain.getCity());
        entity.setUf(domain.getUf());
        entity.setZipCode(domain.getZipCode());
        entity.setComplement(domain.getComplement());
        entity.setRegion(domain.getRegion());

        return entity;
    }
}
