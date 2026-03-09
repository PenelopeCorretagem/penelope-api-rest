package penelope.corretagem.penelopeapirest.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import penelope.corretagem.penelopeapirest.infrastructure.entity.EventTypeJpaEntity;

public interface IEventTypeJpaRepository extends JpaRepository<EventTypeJpaEntity, Long> {
    @Modifying
    @Query(value = "INSERT INTO event_type_entity (id, title, slug) VALUES (:id, :title, :slug)", nativeQuery = true)
    void insertNative(@Param("id") Long id, @Param("title") String title, @Param("slug") String slug);
}