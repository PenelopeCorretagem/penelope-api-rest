package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo", nullable = false)
  private String title;

  @Column(name = "descricao", nullable = false)
  private String description;

  @Column(name = "preco", nullable = false)
  private BigDecimal price;

  @Column(name = "area", nullable = false)
  private Double area;

  @Column(name = "quartos", nullable = false)
  private Integer numberOfRooms;

  @Column(name = "banheiros", nullable = false)
  private Integer numberOfBathrooms;

  @Column(name = "vagas", nullable = false)
  private Integer numberOfVacancies;

  @Column(name = "tipo", nullable = false)
  @Enumerated(EnumType.STRING)
  private Type type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_endereco", referencedColumnName = "id", nullable = false)
  private AddressEntity address;

  @OneToMany(
    mappedBy = "estate",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY)
  private Set<ImageEstateEntity> images;

  @Getter
  public enum Type {
    COMPLETED("pronto"),
    UNDER_CONSTRUCTION("em obras"),
    LAUNCH("lançamento");

    private final String typeName;

    Type(String typeName) {
      this.typeName = typeName;
    }
  }
}
