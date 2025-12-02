package penelope.corretagem.penelopeapirest.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.repository.AdvertisementRepository;
import penelope.corretagem.penelopeapirest.data.domain.specification.AdvertisementSpecifications;
import penelope.corretagem.penelopeapirest.mapper.AdvertisementResponseMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    private final AdvertisementRepository repository;

    public AdvertisementService(AdvertisementRepository repository) {
        this.repository = repository;
    }

    public List<AdvertisementResponse> getAllActiveAdvertisements(AdvertisementFilterRequest request) {

        EstateEntity.Type type = null;
        if (request.type() != null) {
            type = EstateEntity.Type.valueOf(request.type().toUpperCase());
        }

        boolean ativoFinal = request.active() == null || request.active();

        Specification<AdvertisementEntity> spec =
                AdvertisementSpecifications.hasCidade(request.city())
                        .and(AdvertisementSpecifications.hasRegiao(request.region()))
                        .and(AdvertisementSpecifications.hasTipo(type))
                        .and(AdvertisementSpecifications.hasQuartos(request.numberOfRooms()))
                        .and(AdvertisementSpecifications.hasArea(request.area()))
                        .and(AdvertisementSpecifications.hasTitulo(request.title()))
                        .and(AdvertisementSpecifications.hasDescricao(request.description()))
                        .and(AdvertisementSpecifications.isActive(ativoFinal))
                        .and(AdvertisementSpecifications.createdAtEquals(request.createdAt()))
                        .and(AdvertisementSpecifications.createdAtGreaterThan(request.createdAtMin()))
                        .and(AdvertisementSpecifications.createdAtLessThan(request.createdAtMax()))
                        .and(AdvertisementSpecifications.endDateEquals(request.endDate()))
                        .and(AdvertisementSpecifications.endDateGreaterThan(request.endDateMin()))
                        .and(AdvertisementSpecifications.endDateLessThan(request.endDateMax()));

        var anuncios = repository.findAll(spec);

        return anuncios.stream()
                .map(AdvertisementResponseMapper::toDTO)
                .collect(Collectors.toList());
    }


    public AdvertisementResponse getLatestAdvertisement() {
        return repository.findTopByOrderByCreatedAtDesc()
                .map(AdvertisementResponseMapper::toDTO)
                .orElse(null);
    }

    public AdvertisementResponse getAdvertisementById(Long id) {
        return repository.findByIdWithAllRelations(id)
                .map(AdvertisementResponseMapper::toDTO)
                .orElse(null);
    }
}