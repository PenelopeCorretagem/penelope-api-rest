package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;

@Service
public class ChangeAdvertisementStatusUseCase {

    private final IAdvertisementRepository advertisementRepository;

    public ChangeAdvertisementStatusUseCase(IAdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public void execute(Long advertisementId, Boolean active) {
        advertisementRepository.updateStatus(advertisementId, active);
    }
}