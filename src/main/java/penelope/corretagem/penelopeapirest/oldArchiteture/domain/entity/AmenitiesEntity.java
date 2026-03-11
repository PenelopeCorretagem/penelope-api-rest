//package penelope.corretagem.penelopeapirest.data.domain.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;
//
//import java.util.Set;
//
//@Entity
//@Table(name = "diferencial")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class AmenitiesEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "descricao", nullable = false, unique = true)
//    private String description;
//
//    @OneToMany(
//            mappedBy = "amenity",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY)
//    private Set<AmenitiesEstateEntity> properties;
//}
