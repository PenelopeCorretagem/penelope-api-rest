package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "corretor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstateAgentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_usuario", referencedColumnName = "id", nullable = false, unique = true)
  private UserEntity user;

  @Column(name = "nome", nullable = false)
  private String name;

  @Column(name = "creci", unique = true)
  private String creci;

  @Column(name = "telefone")
  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_endereco", referencedColumnName = "id")
  private AddressEntity address;
}