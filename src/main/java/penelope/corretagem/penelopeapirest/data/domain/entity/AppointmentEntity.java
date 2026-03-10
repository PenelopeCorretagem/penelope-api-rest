package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamento")
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
    private UserEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_corretor", referencedColumnName = "id", nullable = false)
    private UserEntity estateAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_empreendimento", referencedColumnName = "id", nullable = false)
    private EstateEntity estate;

    @Column(name = "duracao_minutos")
    private Integer durationMinutes;

    @Column(name = "data_agendamento")
    private LocalDateTime dateAppointment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "data_inicio")
    private LocalDateTime startDateTime;

    @Column(name = "data_fim")
    private LocalDateTime endDateTime;

}

