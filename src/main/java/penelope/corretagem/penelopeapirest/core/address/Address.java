package penelope.corretagem.penelopeapirest.core.address;

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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua", nullable = false)
    private String street;

    @Column(name = "numero", nullable = false)
    private String number;

    @Column(name = "bairro")
    private String neighborhood;

    @Column(name = "cidade", nullable = false)
    private String city;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @Column(name = "cep", nullable = false, length = 8)
    private String zipCode;

    @Column(name = "complemento")
    private String complement;

    @Column(name = "regiao", nullable = false)
    private String region;
}
