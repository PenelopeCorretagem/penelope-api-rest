package penelope.corretagem.penelopeapirest.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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

    public List<AdvertisementResponse> getAllActiveAdvertisements(String cidade, String regiao, String tipoStr, Integer quartos) {
        EstateEntity.Type tipo = null;
        if (tipoStr != null) {
            tipo = EstateEntity.Type.valueOf(tipoStr.toUpperCase());
        }

        Specification<AdvertisementEntity> spec = AdvertisementSpecifications.hasCidade(cidade)
                .and(AdvertisementSpecifications.hasRegiao(regiao))
                .and(AdvertisementSpecifications.hasTipo(tipo))
                .and(AdvertisementSpecifications.hasQuartos(quartos))
                .and((root, query, cb) -> cb.isTrue(root.get("active")));

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
