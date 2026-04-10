package penelope.corretagem.penelopeapirest.infrastructure.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.application.dto.*;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/advertisements")
public class AdvertisementController {

    private final GetAdvertisementByIdUseCase getAdvertisementByIdUseCase;
    private final GetAllAdvertisementsUseCase getAllAdvertisementsUseCase;
    private final GetAdvertisementByEstateIdUseCase getAdvertisementByEstateIdUseCase;
    private final GetLatestAdvertisementUseCase getLatestAdvertisementUseCase;
    private final CreateAdvertisementUseCase createAdvertisementUseCase;
    private final UpdateAdvertisementUseCase updateAdvertisementUseCase;
    private final ChangeAdvertisementStatusUseCase changeAdvertisementStatusUseCase;

    public AdvertisementController(
            GetAdvertisementByIdUseCase getAdvertisementByIdUseCase,
            GetAllAdvertisementsUseCase getAllAdvertisementsUseCase, GetAdvertisementByEstateIdUseCase getAdvertisementByEstateIdUseCase, GetLatestAdvertisementUseCase getLatestAdvertisementUseCase, CreateAdvertisementUseCase createAdvertisementUseCase, UpdateAdvertisementUseCase updateAdvertisementUseCase, ChangeAdvertisementStatusUseCase changeAdvertisementStatusUseCase
    ) {
        this.getAdvertisementByIdUseCase = getAdvertisementByIdUseCase;
        this.getAllAdvertisementsUseCase = getAllAdvertisementsUseCase;
        this.getAdvertisementByEstateIdUseCase = getAdvertisementByEstateIdUseCase;
        this.getLatestAdvertisementUseCase = getLatestAdvertisementUseCase;
        this.createAdvertisementUseCase = createAdvertisementUseCase;
        this.updateAdvertisementUseCase = updateAdvertisementUseCase;
        this.changeAdvertisementStatusUseCase = changeAdvertisementStatusUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementResponse>> getAll(AdvertisementFilterRequest filter) {

        var response = getAllAdvertisementsUseCase.execute(filter);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisementById(@PathVariable Long id) {

        AdvertisementResponse response = getAdvertisementByIdUseCase.execute(id);

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
    public ResponseEntity<AdvertisementResponse> create(@RequestBody AdvertisementCreateRequest request) {

        var savedAdvertisement = createAdvertisementUseCase.execute(request);

        var response = AdvertisementResponse.fromDomain(savedAdvertisement);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid AdvertisementUpdateRequest request) {

        var updatedAdvertisement = updateAdvertisementUseCase.execute(id, request);
        var response = AdvertisementResponse.fromDomain(updatedAdvertisement);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> statusRequest) {

        Boolean isActive = statusRequest.get("active");

        if (isActive == null) {
            return ResponseEntity.badRequest().build();
        }

        changeAdvertisementStatusUseCase.execute(id, isActive);
        return ResponseEntity.noContent().build();
    }
}
