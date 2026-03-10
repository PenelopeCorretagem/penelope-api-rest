package penelope.corretagem.penelopeapirest.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.GetAllAmenitiesUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/v2/diferenciais")
public class AmenitiesControllerV2 {

    private final GetAllAmenitiesUseCase getAllAmenitiesUseCase;

    public AmenitiesControllerV2(GetAllAmenitiesUseCase getAllAmenitiesUseCase) {
        this.getAllAmenitiesUseCase = getAllAmenitiesUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AmenitiesResponse>> getAll() {
        return ResponseEntity.ok(getAllAmenitiesUseCase.execute());
    }
}