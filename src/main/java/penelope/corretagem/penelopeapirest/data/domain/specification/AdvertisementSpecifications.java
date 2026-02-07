package penelope.corretagem.penelopeapirest.data.domain.specification;

import org.springframework.data.jpa.domain.Specification;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;

import java.time.LocalDate;

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

    public static Specification<AdvertisementEntity> hasArea(Double area) {
        return (root, query, cb) -> area == null ? null :
                cb.equal(root.get("property").get("area"), area);
    }

    public static Specification<AdvertisementEntity> hasTitulo(String titulo) {
        return (root, query, cb) -> titulo == null ? null :
                cb.equal(root.get("property").get("title"), titulo);
    }

    public static Specification<AdvertisementEntity> hasDescricao(String descricao) {
        return (root, query, cb) -> descricao == null ? null :
                cb.equal(root.get("property").get("description"), descricao);
    }

    public static Specification<AdvertisementEntity> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null :
                cb.equal(root.get("active"), active);
    }

    public static Specification<AdvertisementEntity> isEmphasis(Boolean emphasis) {
        return (root, query, cb) -> emphasis == null ? null :
                cb.equal(root.get("emphasis"), emphasis);
    }

    public static Specification<AdvertisementEntity> createdAtEquals(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.equal(root.get("createdAt"), createdAt);
    }

    public static Specification<AdvertisementEntity> createdAtGreaterThan(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.greaterThan(root.get("createdAt"), createdAt);
    }

    public static Specification<AdvertisementEntity> createdAtLessThan(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.lessThan(root.get("createdAt"), createdAt);
    }
    public static Specification<AdvertisementEntity> endDateEquals(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.equal(root.get("endDate"), endDate);
    }

    public static Specification<AdvertisementEntity> endDateGreaterThan(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.greaterThan(root.get("endDate"), endDate);
    }

    public static Specification<AdvertisementEntity> endDateLessThan(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.lessThan(root.get("endDate"), endDate);
    }
}