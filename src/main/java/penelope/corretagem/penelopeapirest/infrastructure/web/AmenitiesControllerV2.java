package penelope.corretagem.penelopeapirest.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.GetAllAmenitiesUseCase;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.GetAmenityByIdUseCase;

import java.util.List;

@RestController
@RequestMapping("/amenities")
public class AmenitiesControllerV2 {

    private final GetAllAmenitiesUseCase getAllAmenitiesUseCase;
    private final GetAmenityByIdUseCase getAmenityByIdUseCase;

    public AmenitiesControllerV2(GetAllAmenitiesUseCase getAllAmenitiesUseCase, GetAmenityByIdUseCase getAmenityByIdUseCase) {
        this.getAllAmenitiesUseCase = getAllAmenitiesUseCase;
        this.getAmenityByIdUseCase = getAmenityByIdUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AmenitiesResponse>> getAll() {
        return ResponseEntity.ok(getAllAmenitiesUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenitiesResponse> getAmenityById(@PathVariable Long id){
        return ResponseEntity.ok(getAmenityByIdUseCase.execute(id));
    }
}