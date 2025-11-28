package penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO;

public record AdvertisementFilterRequest(
    String cidade,
    String regiao,
    String tipo,
    Integer quartos,
    Boolean ativo
) {}