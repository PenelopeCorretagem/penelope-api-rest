//package penelope.corretagem.penelopeapirest.data.domain.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import penelope.corretagem.penelopeapirest.core.address.Address;
//import java.util.Set;
//
//@Entity
//@Table(name = "empreendimento")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class EstateEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "titulo", nullable = false)
//    private String title;
//
//    @Lob
//    @Column(name = "descricao", nullable = false)
//    private String description;
//
//    @Column(name = "area", nullable = false)
//    private Double area;
//
//    @Column(name = "quartos", nullable = false)
//    private Integer numberOfRooms;
//
//    @Column(name = "tipo", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Type type;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_endereco", referencedColumnName = "id", nullable = false)
//    private AddressEntity address;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_endereco_stand", referencedColumnName = "id", nullable = true)
//    private AddressEntity standAddress;
//
//    @OneToMany(
//            mappedBy = "estate",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY)
//    private Set<ImageEstateEntity> images;
//
//    @OneToMany(
//            mappedBy = "estate",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY)
//    private Set<AppointmentEntity> appointments;
//
//    @OneToMany(
//            mappedBy = "estate",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY)
//    private Set<AmenitiesEstateEntity> amenities;
//
//    @Getter
//    public enum Type {
//        DISPONIVEL("Disponível"),
//        EM_OBRAS("Em obras"),
//        LANCAMENTO("Lançamento");
//
//        private final String typeName;
//
//        Type(String typeName) {
//            this.typeName = typeName;
//        }
//    }
//}