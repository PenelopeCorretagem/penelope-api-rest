package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;

@Service
public class GetAdvertisementByEstateIdUseCase {

    private final IAdvertisementRepository repository;

    public GetAdvertisementByEstateIdUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    public AdvertisementResponse execute(Long estateId) {
        var advertisement = repository.findByEstateId(estateId);

        if (advertisement == null) {
            throw new RuntimeException("Nenhum anúncio encontrado para o Empreendimento ID: " + estateId);
        }

        return AdvertisementResponse.fromDomain(advertisement);
    }
}