//package penelope.corretagem.penelopeapirest.infrastructure.specification;
//
//import jakarta.persistence.criteria.*;
//import org.springframework.data.jpa.domain.Specification;
//import penelope.corretagem.penelopeapirest.core.user.User;
//import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
//import penelope.corretagem.penelopeapirest.core.estate.Estate;
//import penelope.corretagem.penelopeapirest.data.domain.enums.Status;
//
//import java.time.LocalDateTime;
//
//public class AppointmentSpecification {
//
//    public static Specification<AppointmentEntity> withFilters(
//            Long clientId,
//            Long estateAgentId,
//            Long estateId,
//            Status status,
//            LocalDateTime startDate,
//            LocalDateTime endDate,
//            String clientEmail,
//            String agentEmail,
//            String estateTitle) {
//
//        return Specification
//                .where(hasClientId(clientId))
//                .and(hasEstateAgentId(estateAgentId))
//                .and(hasEstateId(estateId))
//                .and(hasStatus(status))
//                .and(hasStartDateAfter(startDate))
//                .and(hasStartDateBefore(endDate))
//                .and(hasClientEmail(clientEmail))
//                .and(hasAgentEmail(agentEmail))
//                .and(hasEstateTitle(estateTitle));
//    }
//
//    public static Specification<AppointmentEntity> hasClientId(Long clientId) {
//        return (root, query, criteriaBuilder) -> {
//            if (clientId == null) {
//                return criteriaBuilder.conjunction();
//            }
//            Join<AppointmentEntity, User> client = root.join("client");
//            return criteriaBuilder.equal(client.get("id"), clientId);
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasEstateAgentId(Long estateAgentId) {
//        return (root, query, criteriaBuilder) -> {
//            if (estateAgentId == null) {
//                return criteriaBuilder.conjunction();
//            }
//            Join<AppointmentEntity, User> agent = root.join("estateAgent");
//            return criteriaBuilder.equal(agent.get("id"), estateAgentId);
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasEstateId(Long estateId) {
//        return (root, query, criteriaBuilder) -> {
//            if (estateId == null) {
//                return criteriaBuilder.conjunction();
//            }
//            Join<AppointmentEntity, Estate> estate = root.join("estate");
//            return criteriaBuilder.equal(estate.get("id"), estateId);
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasStatus(Status status) {
//        return (root, query, criteriaBuilder) -> {
//            if (status == null) {
//                return criteriaBuilder.conjunction();
//            }
//            return criteriaBuilder.equal(root.get("status"), status);
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasStartDateAfter(LocalDateTime startDate) {
//        return (root, query, criteriaBuilder) -> {
//            if (startDate == null) {
//                return criteriaBuilder.conjunction();
//            }
//            return criteriaBuilder.greaterThanOrEqualTo(root.get("startDateTime"), startDate);
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasStartDateBefore(LocalDateTime endDate) {
//        return (root, query, criteriaBuilder) -> {
//            if (endDate == null) {
//                return criteriaBuilder.conjunction();
//            }
//            return criteriaBuilder.lessThanOrEqualTo(root.get("startDateTime"), endDate);
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasClientEmail(String clientEmail) {
//        return (root, query, criteriaBuilder) -> {
//            if (clientEmail == null || clientEmail.trim().isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            Join<AppointmentEntity, User> client = root.join("client");
//            return criteriaBuilder.like(
//                    criteriaBuilder.lower(client.get("email")),
//                    "%" + clientEmail.toLowerCase() + "%"
//            );
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasAgentEmail(String agentEmail) {
//        return (root, query, criteriaBuilder) -> {
//            if (agentEmail == null || agentEmail.trim().isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            Join<AppointmentEntity, User> agent = root.join("estateAgent");
//            return criteriaBuilder.like(
//                    criteriaBuilder.lower(agent.get("email")),
//                    "%" + agentEmail.toLowerCase() + "%"
//            );
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasEstateTitle(String estateTitle) {
//        return (root, query, criteriaBuilder) -> {
//            if (estateTitle == null || estateTitle.trim().isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            Join<AppointmentEntity, Estate> estate = root.join("estate");
//            return criteriaBuilder.like(
//                    criteriaBuilder.lower(estate.get("title")),
//                    "%" + estateTitle.toLowerCase() + "%"
//            );
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasCalBookingId() {
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.isNotNull(root.get("calBookingId"));
//    }
//
//    public static Specification<AppointmentEntity> hasNoCalBookingId() {
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.isNull(root.get("calBookingId"));
//    }
//
//    public static Specification<AppointmentEntity> isActive() {
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.notEqual(root.get("status"), Status.CANCELLED);
//    }
//
//    public static Specification<AppointmentEntity> isInDateRange(LocalDateTime start, LocalDateTime end) {
//        return (root, query, criteriaBuilder) -> {
//            if (start == null && end == null) {
//                return criteriaBuilder.conjunction();
//            }
//            if (start != null && end != null) {
//                return criteriaBuilder.between(root.get("startDateTime"), start, end);
//            }
//            if (start != null) {
//                return criteriaBuilder.greaterThanOrEqualTo(root.get("startDateTime"), start);
//            }
//            return criteriaBuilder.lessThanOrEqualTo(root.get("startDateTime"), end);
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasClientName(String clientName) {
//        return (root, query, criteriaBuilder) -> {
//            if (clientName == null || clientName.trim().isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            Join<AppointmentEntity, User> client = root.join("client");
//            return criteriaBuilder.like(
//                    criteriaBuilder.lower(client.get("name")),
//                    "%" + clientName.toLowerCase() + "%"
//            );
//        };
//    }
//
//    public static Specification<AppointmentEntity> hasAgentName(String agentName) {
//        return (root, query, criteriaBuilder) -> {
//            if (agentName == null || agentName.trim().isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            Join<AppointmentEntity, User> agent = root.join("estateAgent");
//            return criteriaBuilder.like(
//                    criteriaBuilder.lower(agent.get("name")),
//                    "%" + agentName.toLowerCase() + "%"
//            );
//        };
//    }
//}