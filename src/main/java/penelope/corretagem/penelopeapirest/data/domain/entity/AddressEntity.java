package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "rua", nullable = false)
  private String street;

  @Column(name = "numero")
  private String number;

  @Column(name = "bairro")
  private String neighborhood;

  @Column(name = "cep", nullable = false, length = 8)
  private String zipCode;

  @Column(name = "complemento")
  private String complement;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_cidade", referencedColumnName = "id", nullable = false)
  private CityEntity city;
}
