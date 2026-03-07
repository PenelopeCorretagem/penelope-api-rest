package penelope.corretagem.penelopeapirest.data.domain.specification;

import org.springframework.data.jpa.domain.Specification;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.estate.Estate;

import java.time.LocalDate;

import static penelope.corretagem.penelopeapirest.util.StringUtils.normalize;

public class AdvertisementSpecifications {

    public static Specification<Advertisement> hasCidade(String cidade) {
        return (root, query, cb) -> {
            if (cidade == null) return null;

            String cidadeNormalizada = normalize(cidade);
            return cb.equal(
                    cb.lower(root.get("property").get("address").get("city")),
                    cidadeNormalizada
            );
        };
    }

    public static Specification<Advertisement> hasRegiao(String regiao) {
        return (root, query, cb) -> {
            if (regiao == null) return null;

            String regiaoNormalizada = normalize(regiao);
            return cb.equal(
                    cb.lower(root.get("property").get("address").get("region")),
                    regiaoNormalizada
            );
        };
    }

    public static Specification<Advertisement> hasTipo(Estate.Type tipo) {
        return (root, query, cb) -> tipo == null ? null :
                cb.equal(root.get("property").get("type"), tipo);
    }

    public static Specification<Advertisement> hasQuartos(Integer quartos) {
        return (root, query, cb) -> quartos == null ? null :
                cb.equal(root.get("property").get("numberOfRooms"), quartos);
    }

    public static Specification<Advertisement> hasArea(Double area) {
        return (root, query, cb) -> area == null ? null :
                cb.equal(root.get("property").get("area"), area);
    }

    public static Specification<Advertisement> hasTitulo(String titulo) {
        return (root, query, cb) -> titulo == null ? null :
                cb.equal(root.get("property").get("title"), titulo);
    }

    public static Specification<Advertisement> hasDescricao(String descricao) {
        return (root, query, cb) -> descricao == null ? null :
                cb.equal(root.get("property").get("description"), descricao);
    }

    public static Specification<Advertisement> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null :
                cb.equal(root.get("active"), active);
    }

    public static Specification<Advertisement> isEmphasis(Boolean emphasis) {
        return (root, query, cb) -> emphasis == null ? null :
                cb.equal(root.get("emphasis"), emphasis);
    }

    public static Specification<Advertisement> createdAtEquals(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.equal(root.get("createdAt"), createdAt);
    }

    public static Specification<Advertisement> createdAtGreaterThan(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.greaterThan(root.get("createdAt"), createdAt);
    }

    public static Specification<Advertisement> createdAtLessThan(LocalDate createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null : cb.lessThan(root.get("createdAt"), createdAt);
    }
    public static Specification<Advertisement> endDateEquals(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.equal(root.get("endDate"), endDate);
    }

    public static Specification<Advertisement> endDateGreaterThan(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.greaterThan(root.get("endDate"), endDate);
    }

    public static Specification<Advertisement> endDateLessThan(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.lessThan(root.get("endDate"), endDate);
    }
}