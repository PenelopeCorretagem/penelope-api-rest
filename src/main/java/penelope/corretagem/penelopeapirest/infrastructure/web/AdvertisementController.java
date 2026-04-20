package penelope.corretagem.penelopeapirest.infrastructure.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.application.dto.*;
import penelope.corretagem.penelopeapirest.application.useCase.advertisement.*;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;

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
    private final DeleteAdvertisementUseCase deleteAdvertisementUseCase;
    private final UpdateAdvertisementUseCase updateAdvertisementUseCase;
    private final ChangeAdvertisementStatusUseCase changeAdvertisementStatusUseCase;
    private final ITokenGateway tokenGateway;

    public AdvertisementController(
            GetAdvertisementByIdUseCase getAdvertisementByIdUseCase,
            GetAllAdvertisementsUseCase getAllAdvertisementsUseCase,
            GetAdvertisementByEstateIdUseCase getAdvertisementByEstateIdUseCase,
            GetLatestAdvertisementUseCase getLatestAdvertisementUseCase,
            CreateAdvertisementUseCase createAdvertisementUseCase,
            DeleteAdvertisementUseCase deleteAdvertisementUseCase,
            UpdateAdvertisementUseCase updateAdvertisementUseCase,
            ChangeAdvertisementStatusUseCase changeAdvertisementStatusUseCase,
            ITokenGateway tokenGateway
    ) {
        this.getAdvertisementByIdUseCase = getAdvertisementByIdUseCase;
        this.getAllAdvertisementsUseCase = getAllAdvertisementsUseCase;
        this.getAdvertisementByEstateIdUseCase = getAdvertisementByEstateIdUseCase;
        this.getLatestAdvertisementUseCase = getLatestAdvertisementUseCase;
        this.createAdvertisementUseCase = createAdvertisementUseCase;
        this.deleteAdvertisementUseCase = deleteAdvertisementUseCase;
        this.updateAdvertisementUseCase = updateAdvertisementUseCase;
        this.changeAdvertisementStatusUseCase = changeAdvertisementStatusUseCase;
        this.tokenGateway = tokenGateway;
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementResponse>> getAll(AdvertisementFilterRequest filter) {

        var response = getAllAdvertisementsUseCase.execute(filter);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisementById(
            @PathVariable Long id,
            Authentication authentication,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        AdvertisementResponse response = getAdvertisementByIdUseCase.execute(id, resolveIsAdministrator(authentication, authorizationHeader));

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteAdvertisementUseCase.execute(id);
        return ResponseEntity.noContent().build();
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

    private boolean isAdministrator(Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMINISTRADOR".equals(authority.getAuthority()));
    }

    private boolean resolveIsAdministrator(Authentication authentication, String authorizationHeader) {
        if (isAdministrator(authentication)) {
            return true;
        }

        String token = extractBearerToken(authorizationHeader);
        if (token == null) {
            return false;
        }

        try {
            String accessLevel = tokenGateway.getAccessLevelFromToken(token);
            return accessLevel != null && "ADMINISTRADOR".equalsIgnoreCase(accessLevel.trim());
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }

        String trimmedHeader = authorizationHeader.trim();
        if (trimmedHeader.length() < 7 || !trimmedHeader.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return null;
        }

        return trimmedHeader.substring(7).trim();
    }
}
