package penelope.corretagem.penelopeapirest.data.domain.specification;

import org.springframework.data.jpa.domain.Specification;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;

public class AdvertisementSpecifications {
    public static Specification<AdvertisementEntity> hasCidade(String cidade) {
        return (root, query, cb) -> cidade == null ? null :
                cb.equal(cb.lower(root.get("property").get("address").get("city")), cidade.toLowerCase());
    }

    public static Specification<AdvertisementEntity> hasRegiao(String regiao) {
        return (root, query, cb) -> regiao == null ? null :
                cb.equal(cb.lower(root.get("property").get("address").get("region")), regiao.toLowerCase());
    }

    public static Specification<AdvertisementEntity> hasTipo(EstateEntity.Type tipo) {
        return (root, query, cb) -> tipo == null ? null :
                cb.equal(root.get("property").get("type"), tipo);
    }

    public static Specification<AdvertisementEntity> hasQuartos(Integer quartos) {
        return (root, query, cb) -> quartos == null ? null :
                cb.equal(root.get("property").get("numberOfRooms"), quartos);
    }
}
