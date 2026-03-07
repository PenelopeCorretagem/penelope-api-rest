package penelope.corretagem.penelopeapirest.infrastucture.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import penelope.corretagem.penelopeapirest.application.useCase.GetAdvertisementByIdUseCase;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    private final GetAdvertisementByIdUseCase getAdvertisementByIdUseCase;

    public AdvertisementController(GetAdvertisementByIdUseCase getAdvertisementByIdUseCase) {
        this.getAdvertisementByIdUseCase = getAdvertisementByIdUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisementById(@PathVariable Long id) {

        AdvertisementResponse response = getAdvertisementByIdUseCase.execute(id);

        return ResponseEntity.ok(response);
    }
}
