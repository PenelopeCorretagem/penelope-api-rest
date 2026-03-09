package penelope.corretagem.penelopeapirest.core.address;

public class Address {

    private final Long id;
    private final String street;
    private final String number;
    private final String neighborhood;
    private final String city;
    private final String uf;
    private final String zipCode;
    private final String complement;
    private final String region;

    private Address(Long id, String street, String number, String neighborhood, String city, String uf, String zipCode, String complement, String region) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.zipCode = zipCode;
        this.complement = complement;
        this.region = region;
    }

    public static Address createNew(String street, String number, String neighborhood, String city, String uf, String zipCode, String complement, String region) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("A rua não pode estar vazia.");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("O CEP não pode estar vazio.");
        }

        return new Address(null, street, number, neighborhood, city, uf, zipCode, complement, region);
    }

    public static Address restore(Long id, String street, String number, String neighborhood, String city, String uf, String zipCode, String complement, String region) {
        return new Address(id, street, number, neighborhood, city, uf, zipCode, complement, region);
    }

    public Long getId() { return id; }
    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getNeighborhood() { return neighborhood; }
    public String getCity() { return city; }
    public String getUf() { return uf; }
    public String getZipCode() { return zipCode; }
    public String getComplement() { return complement; }
    public String getRegion() { return region; }
}