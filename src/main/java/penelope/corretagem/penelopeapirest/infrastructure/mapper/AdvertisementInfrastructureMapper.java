package penelope.corretagem.penelopeapirest.infrastructure.mapper;

import org.springframework.stereotype.Component;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.infrastructure.entity.*;

@Component
public class AdvertisementInfrastructureMapper {

    private final UserInfrastructureMapper userMapper;
    private final EstateInfrastructureMapper estateMapper;

    public AdvertisementInfrastructureMapper(
            UserInfrastructureMapper userMapper,
            EstateInfrastructureMapper estateMapper
    ) {
        this.userMapper = userMapper;
        this.estateMapper = estateMapper;
    }

    public Advertisement toDomain(AdvertisementJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        return Advertisement.restore(
                jpaEntity.getId(),
                estateMapper.toDomain(jpaEntity.getEstate()),
                userMapper.toDomain(jpaEntity.getCreator()),
                userMapper.toDomain(jpaEntity.getResponsible()),
                jpaEntity.getActive(),
                jpaEntity.getEmphasis(),
                jpaEntity.getEndDate(),
                jpaEntity.getCreatedAt()
        );
    }

    public AdvertisementJpaEntity toEntity(Advertisement domain) {
        if (domain == null) return null;

        var entity = new AdvertisementJpaEntity();
        entity.setId(domain.getId());

        EstateJpaEntity estateEntity = estateMapper.toEntity(domain.getEstate());

        entity.setEstate(estateEntity);
        entity.setCreator(userMapper.toEntity(domain.getCreator()));
        entity.setResponsible(userMapper.toEntity(domain.getResponsible()));

        entity.setActive(domain.getActive());
        entity.setEmphasis(domain.getEmphasis());
        entity.setEndDate(domain.getEndDate());
        entity.setCreatedAt(domain.getCreatedAt());

        return entity;
    }
}