//package penelope.corretagem.penelopeapirest.data.domain.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import penelope.corretagem.penelopeapirest.core.user.User;
//import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
//import penelope.corretagem.penelopeapirest.data.domain.entity.UserEntity;
//import penelope.corretagem.penelopeapirest.data.domain.enums.Status;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>, JpaSpecificationExecutor<AppointmentEntity> {
//
//    Optional<AppointmentEntity> findByCalBookingId(Long calBookingId);
//
//    List<AppointmentEntity> findByClientAndStatus(UserEntity client, Status status);
//
//    Optional<AppointmentEntity> findByClientAndStartDateTime(UserEntity client, LocalDateTime startDateTime);
//}
