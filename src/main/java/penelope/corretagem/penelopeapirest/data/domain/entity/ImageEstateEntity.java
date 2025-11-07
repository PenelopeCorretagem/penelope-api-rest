package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "imagem_imovel")
public class ImageEstateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "url", nullable = false)
  String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_imovel", referencedColumnName = "id", nullable = false)
  EstateEntity estate;
}