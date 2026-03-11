//package penelope.corretagem.penelopeapirest.data.domain.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import penelope.corretagem.penelopeapirest.core.estate.Estate;
//import penelope.corretagem.penelopeapirest.core.estate.ImageEstateType;
//
//@Entity
//@Table(name = "imagem_empreendimento")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class ImageEstateEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_empreendimento", referencedColumnName = "id", nullable = false)
//    private EstateEntity estate;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_tipo_imagem", referencedColumnName = "id", nullable = false)
//    private ImageEstateTypeEntity type;
//
//    @Column(name = "url", nullable = false)
//    private String url;
//}
