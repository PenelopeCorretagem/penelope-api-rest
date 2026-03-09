package penelope.corretagem.penelopeapirest.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.application.dto.EstateCreateRequest;
import penelope.corretagem.penelopeapirest.application.useCase.*;
import penelope.corretagem.penelopeapirest.core.dto.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;

import java.util.List;

@RestController("advertisementControllerV2")
@RequestMapping("/api/v2/anuncios")
public class AdvertisementControllerV2 {

    private final GetAdvertisementByIdUseCase getAdvertisementByIdUseCase;
    private final GetAllAdvertisementsUseCase getAllAdvertisementsUseCase;
    private final GetAdvertisementByEstateIdUseCase getAdvertisementByEstateIdUseCase;
    private final GetLatestAdvertisementUseCase getLatestAdvertisementUseCase;
    private final CreateAdvertisementUseCase createAdvertisementUseCase;

    public AdvertisementControllerV2(
            GetAdvertisementByIdUseCase getAdvertisementByIdUseCase,
            GetAllAdvertisementsUseCase getAllAdvertisementsUseCase, GetAdvertisementByEstateIdUseCase getAdvertisementByEstateIdUseCase, GetLatestAdvertisementUseCase getLatestAdvertisementUseCase, CreateAdvertisementUseCase createAdvertisementUseCase
    ) {
        this.getAdvertisementByIdUseCase = getAdvertisementByIdUseCase;
        this.getAllAdvertisementsUseCase = getAllAdvertisementsUseCase;
        this.getAdvertisementByEstateIdUseCase = getAdvertisementByEstateIdUseCase;
        this.getLatestAdvertisementUseCase = getLatestAdvertisementUseCase;
        this.createAdvertisementUseCase = createAdvertisementUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisementById(@PathVariable Long id) {

        AdvertisementResponse response = getAdvertisementByIdUseCase.execute(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementResponse>> getAll(AdvertisementFilterRequest filter) {

        var response = getAllAdvertisementsUseCase.execute(filter);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<AdvertisementResponse> getLatest() {
        AdvertisementResponse response = getLatestAdvertisementUseCase.execute();
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }

    @GetMapping("/estate/{estateId}")
    public ResponseEntity<AdvertisementResponse> getByEstateId(@PathVariable Long estateId) {
        return ResponseEntity.ok(getAdvertisementByEstateIdUseCase.execute(estateId));
    }

    @PostMapping
    public ResponseEntity<AdvertisementResponse> create(@RequestBody EstateCreateRequest request) {

        var savedAdvertisement = createAdvertisementUseCase.execute(request);

        var response = AdvertisementResponse.fromDomain(savedAdvertisement);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
