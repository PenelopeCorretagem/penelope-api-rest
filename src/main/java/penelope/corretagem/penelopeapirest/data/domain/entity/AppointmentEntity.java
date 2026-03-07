package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "visita")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_corretor", referencedColumnName = "id", nullable = false)
    private User estateAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_empreendimento", referencedColumnName = "id", nullable = false)
    private Estate estate;

    @Column(name = "duracao_minutos")
    private Integer durationMinutes;

    @Column(name = "data_agendamento")
    private LocalDateTime dateAppointment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Column(name = "cal_booking_id")
    private Long calBookingId;
}

