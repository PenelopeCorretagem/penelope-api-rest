package penelope.corretagem.penelopeapirest.service;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateAgentRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateAgentResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateAgentEntity;
import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;
import penelope.corretagem.penelopeapirest.service.exception.AcessLevelException;
import penelope.corretagem.penelopeapirest.service.exception.EstateAgentNotFoundException;
import penelope.corretagem.penelopeapirest.mapper.EstateAgentMapper;
import penelope.corretagem.penelopeapirest.data.domain.repository.EstateAgentRepository;
import penelope.corretagem.penelopeapirest.service.exception.UserNotFoundException;

import java.util.List;

@Service
public class EstateAgentService {

  private final EstateAgentRepository repository;
  private final EstateAgentMapper mapper;

  public EstateAgentService(EstateAgentRepository repository,
                            EstateAgentMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<EstateAgentResponse> getAllEstateAgents() {
    return repository.findAll().stream()
      .map(mapper::toResponse)
      .toList();
  }

  public EstateAgentResponse getEstateAgentById(Long id) {
    return repository.findById(id)
      .map(mapper::toResponse)
      .orElseThrow(EstateAgentNotFoundException::new);
  }

  public EstateAgentResponse createEstateAgent(EstateAgentRequest request) {
    EstateAgentEntity entity = mapper.toEntity(request);

    if (entity.getUser() == null) {
      throw new UserNotFoundException();
    }

    if (!entity.getUser().getAccessLevel().equals(AccessLevel.ESTATE_AGENT)) {
      throw new AcessLevelException();
    }

    if (entity.getId() != null && repository.existsById(entity.getId())) {
      throw new EstateAgentNotFoundException();
    }

    repository.save(entity);
    return mapper.toResponse(entity);
  }

  public EstateAgentResponse updateEstateAgent(Long id, EstateAgentRequest request) {
    EstateAgentEntity entity = repository.findById(id)
      .orElseThrow(EstateAgentNotFoundException::new);

    mapper.updateEntityFromRequest(request, entity);
    repository.save(entity);

    return mapper.toResponse(entity);
  }

  public void deleteEstateAgent(Long id) {
    EstateAgentEntity entity = repository.findById(id)
      .orElseThrow(EstateAgentNotFoundException::new);

    repository.delete(entity);
  }
}
