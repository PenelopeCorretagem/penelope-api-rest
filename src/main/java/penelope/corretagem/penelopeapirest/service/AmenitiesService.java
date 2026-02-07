package penelope.corretagem.penelopeapirest.service;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.data.domain.repository.AmenitiesRepository;

import java.util.List;

@Service
public class AmenitiesService {

    private final AmenitiesRepository amenitiesRepository;

    public AmenitiesService(AmenitiesRepository amenitiesRepository) {
        this.amenitiesRepository = amenitiesRepository;
    }

    public List<AmenitiesResponse> getAllAmenities() {
        return amenitiesRepository.findAll()
                .stream()
                .map(a -> new AmenitiesResponse(
                        a.getId(),
                        a.getDescription()
                ))
                .toList();
    }

}
