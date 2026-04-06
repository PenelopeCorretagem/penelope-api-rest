package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;

public class GetAdvertisementByEstateIdUseCase {

    private final IAdvertisementRepository repository;

    public GetAdvertisementByEstateIdUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    public AdvertisementResponse execute(Long estateId) {
        if (estateId == null || estateId <= 0) {
            throw new DomainValidationException("ID do empreendimento inválido");
        }

        var advertisement = repository.findByEstateId(estateId);

        if (advertisement == null) {
            throw new ResourceNotFoundException("Nenhum anúncio encontrado para o Empreendimento ID: " + estateId);
        }

        return AdvertisementResponse.fromDomain(advertisement);
    }
}