package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estate_id", nullable = false)
  private EstateEntity estate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estate_agent_id", nullable = false)
  private EstateAgentEntity estateAgent;

  @Enumerated(EnumType.STRING)
  private Status status;

  private LocalDateTime startDateTime;

  private LocalDateTime endDateTime;
}

