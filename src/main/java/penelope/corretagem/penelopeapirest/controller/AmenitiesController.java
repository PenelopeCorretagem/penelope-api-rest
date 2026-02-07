package penelope.corretagem.penelopeapirest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.service.AmenitiesService;

import java.util.List;

@RestController
@RequestMapping("/amenities")
public class AmenitiesController {

    private final AmenitiesService amenitiesService;

    public AmenitiesController(AmenitiesService amenitiesService) {
        this.amenitiesService = amenitiesService;
    }

    @GetMapping
    public ResponseEntity<List<AmenitiesResponse>> getAmenities() {

        List<AmenitiesResponse> amenities = amenitiesService.getAllAmenities();

        if (amenities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(amenities);
    }
}
