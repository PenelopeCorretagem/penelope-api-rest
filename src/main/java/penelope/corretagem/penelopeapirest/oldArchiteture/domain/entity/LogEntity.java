//package penelope.corretagem.penelopeapirest.data.domain.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "log")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class LogEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String origem;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_usuario")
//    private UserEntity usuario;
//
//    @Column(nullable = false)
//    private String detalhes;
//
//    @Column(name = "data_hora", nullable = false)
//    private LocalDateTime dataHora = LocalDateTime.now();
//}
