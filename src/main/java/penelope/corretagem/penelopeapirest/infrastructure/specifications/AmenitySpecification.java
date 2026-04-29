package penelope.corretagem.penelopeapirest.infrastructure.specifications;

import org.springframework.data.jpa.domain.Specification;
import penelope.corretagem.penelopeapirest.infrastructure.entity.AmenitiesJpaEntity;

public class AmenitySpecification {

    public static Specification<AmenitiesJpaEntity> search(String name, String initial) {
        return (root, query, criteriaBuilder) -> {
            Specification<AmenitiesJpaEntity> spec = Specification.where(null);

            if (name != null && !name.isEmpty()) {
                spec = spec.and((r, q, cb) -> cb.like(cb.lower(r.get("description")), "%" + name.toLowerCase() + "%"));
            }

            if (initial != null && !initial.isEmpty()) {
                spec = spec.and((r, q, cb) -> cb.like(cb.lower(r.get("description")), initial.toLowerCase() + "%"));
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
