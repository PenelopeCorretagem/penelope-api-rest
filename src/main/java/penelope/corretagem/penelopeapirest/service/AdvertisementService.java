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

        EstateEntity.Type tipo = null;
        if (request.tipo() != null) {
            tipo = EstateEntity.Type.valueOf(request.tipo().toUpperCase());
        }

        // se o parâmetro for nulo, assume true
        boolean ativoFinal = (request.ativo() == null) ? true : request.ativo();

        Specification<AdvertisementEntity> spec = AdvertisementSpecifications.hasCidade(request.cidade())
                .and(AdvertisementSpecifications.hasRegiao(request.regiao()))
                .and(AdvertisementSpecifications.hasTipo(tipo))
                .and(AdvertisementSpecifications.hasQuartos(request.quartos()))
                .and((root, query, cb) -> cb.equal(root.get("active"), ativoFinal));

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
