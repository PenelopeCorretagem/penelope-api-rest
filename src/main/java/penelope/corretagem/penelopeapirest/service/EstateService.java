package penelope.corretagem.penelopeapirest.service;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.mapper.EstateMapper;
import penelope.corretagem.penelopeapirest.data.domain.repository.EstateRepository;

@Service
public class EstateService {

  private final EstateRepository estateRepository;
  private final EstateMapper estateMapper;

  public EstateService(EstateRepository estateRepository,
                       EstateMapper estateMapper) {

    this.estateRepository = estateRepository;
    this.estateMapper = estateMapper;
  }

//  public List<EstateResponse> getAllEstates() {
//    return estateRepository.findAll().stream()
//      .map(estateMapper::toResponse)
//      .collect(Collectors.toList());
//  }
//
//  public EstateResponse getEstateById(Long id) {
//    return estateRepository.findById(id)
//      .map(estateMapper::toResponse)
//      .orElseThrow(EstateNotFoundException::new);
//  }
//
//  public EstateResponse createEstate(EstateRequest estateRequest) {
//    EstateEntity estate = estateMapper.toEntity(estateRequest);
//
//    if (estate.getId() != null && estateRepository.existsById(estate.getId())) {
//      throw new IllegalArgumentException("Empreendimento já existe");
//    }
//
//    EstateEntity savedEntity = estateRepository.save(estate);
//    return estateMapper.toResponse(savedEntity);
//  }
//
//  public EstateResponse updateEstate(Long id, EstateRequest estateRequest) {
//    EstateEntity existingEstate = estateRepository.findById(id)
//      .orElseThrow(EstateNotFoundException::new);
//
//    estateMapper.updateEntityFromRequest(estateRequest, existingEstate);
//    EstateEntity updatedEntity = estateRepository.save(existingEstate);
//    return estateMapper.toResponse(updatedEntity);
//  }
//
//  public void deleteEstate(Long id) {
//    EstateEntity existingEstate = estateRepository.findById(id)
//      .orElseThrow(EstateNotFoundException::new);
//
//    estateRepository.delete(existingEstate);
//  }
}

