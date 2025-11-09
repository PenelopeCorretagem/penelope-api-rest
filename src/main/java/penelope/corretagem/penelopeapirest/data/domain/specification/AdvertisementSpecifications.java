package penelope.corretagem.penelopeapirest.data.domain.specification;

import org.springframework.data.jpa.domain.Specification;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;

import static penelope.corretagem.penelopeapirest.util.StringUtils.normalize;

public class AdvertisementSpecifications {

    public static Specification<AdvertisementEntity> hasCidade(String cidade) {
        return (root, query, cb) -> {
            if (cidade == null) return null;

            String cidadeNormalizada = normalize(cidade);
            return cb.equal(
                    cb.lower(root.get("property").get("address").get("city")),
                    cidadeNormalizada
            );
        };
    }

    public static Specification<AdvertisementEntity> hasRegiao(String regiao) {
        return (root, query, cb) -> {
            if (regiao == null) return null;

            String regiaoNormalizada = normalize(regiao);
            return cb.equal(
                    cb.lower(root.get("property").get("address").get("region")),
                    regiaoNormalizada
            );
        };
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
