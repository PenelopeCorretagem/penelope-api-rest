package penelope.corretagem.penelopeapirest.infrastructure.specification;

import org.springframework.data.jpa.domain.Specification;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AdvertisementJpaEntity;
import penelope.corretagem.penelopeapirest.infrastructure.entity.EstateJpaEntity;

import java.time.LocalDate;

import static penelope.corretagem.penelopeapirest.util.StringUtils.normalize;

public class AdvertisementSpecifications {

    public static Specification<AdvertisementJpaEntity> hasCidade(String cidade) {
        return (root, query, cb) -> {
            if (cidade == null) return null;

            String cidadeNormalizada = normalize(cidade);
            return cb.equal(
                    cb.lower(root.get("estate").get("address").get("city")),
                    cidadeNormalizada
            );
        };
    }

    public static Specification<AdvertisementJpaEntity> hasRegiao(String regiao) {
        return (root, query, cb) -> {
            if (regiao == null) return null;

            String regiaoNormalizada = normalize(regiao);
            return cb.equal(
                    cb.lower(root.get("estate").get("address").get("region")),
                    regiaoNormalizada
            );
        };
    }

    public static Specification<AdvertisementJpaEntity> hasTipo(EstateJpaEntity.Type tipo) {
        return (root, query, cb) -> tipo == null ? null :
                cb.equal(root.get("estate").get("type"), tipo);
    }

    public static Specification<AdvertisementJpaEntity> hasQuartos(Integer quartos) {
        return (root, query, cb) -> quartos == null ? null :
                cb.equal(root.get("estate").get("numberOfRooms"), quartos);
    }

    public static Specification<AdvertisementJpaEntity> hasArea(Double area) {
        return (root, query, cb) -> area == null ? null :
                cb.equal(root.get("estate").get("area"), area);
    }

    public static Specification<AdvertisementJpaEntity> hasTitulo(String titulo) {
        return (root, query, cb) -> titulo == null ? null :
                cb.equal(root.get("estate").get("title"), titulo);
    }

    public static Specification<AdvertisementJpaEntity> hasDescricao(String descricao) {
        return (root, query, cb) -> descricao == null ? null :
                cb.equal(root.get("estate").get("description"), descricao);
    }

    public static Specification<AdvertisementJpaEntity> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null :
                cb.equal(root.get("active"), active);
    }

    public static Specification<AdvertisementJpaEntity> isEmphasis(Boolean emphasis) {
        return (root, query, cb) -> emphasis == null ? null :
                cb.equal(root.get("emphasis"), emphasis);
    }

    public static Specification<AdvertisementJpaEntity> createdAtEquals(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.equal(root.get("createdAt"), createdAt);
    }

    public static Specification<AdvertisementJpaEntity> createdAtGreaterThan(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.greaterThan(root.get("createdAt"), createdAt);
    }

    public static Specification<AdvertisementJpaEntity> createdAtLessThan(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.lessThan(root.get("createdAt"), createdAt);
    }

    public static Specification<AdvertisementJpaEntity> endDateEquals(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.equal(root.get("endDate"), endDate);
    }

    public static Specification<AdvertisementJpaEntity> endDateGreaterThan(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.greaterThan(root.get("endDate"), endDate);
    }

    public static Specification<AdvertisementJpaEntity> endDateLessThan(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.lessThan(root.get("endDate"), endDate);
    }
}