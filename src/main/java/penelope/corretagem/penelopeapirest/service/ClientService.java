package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.data.domain.dto.ClientRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.ClientResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.ClientEntity;
import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;
import penelope.corretagem.penelopeapirest.data.domain.repository.ClientRepository;
import penelope.corretagem.penelopeapirest.mapper.ClientMapper;
import penelope.corretagem.penelopeapirest.service.exception.AcessLevelException;
import penelope.corretagem.penelopeapirest.service.exception.ClientNotFoundException;
import penelope.corretagem.penelopeapirest.service.exception.ClientAlreadyExistsException;
import penelope.corretagem.penelopeapirest.service.exception.UserNotFoundException;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    public ClientService(ClientRepository repository,
                              ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public List<ClientResponse> getAllClients() {
        return repository.findAll().stream()
          .map(mapper::toResponse)
          .toList();
    }

    public ClientResponse getClientById(Long id) {
        return repository.findById(id)
          .map(mapper::toResponse)
          .orElseThrow(ClientNotFoundException::new);
    }

    @Transactional
    public ClientResponse createClient(ClientRequest request) {
        ClientEntity entity = mapper.toEntity(request);

        if (entity.getUser() == null) {
            throw new UserNotFoundException();
        }

        if (!entity.getUser().getAccessLevel().equals(AccessLevel.CLIENT)) {
            throw new AcessLevelException();
        }

        if (entity.getId() != null && repository.existsById(entity.getId())) {
            throw new ClientAlreadyExistsException();
        }

        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Transactional
    public ClientResponse updateClient(Long id, ClientRequest request) {
        ClientEntity entity = repository.findById(id)
          .orElseThrow(ClientNotFoundException::new);

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Transactional
    public void deleteClient(Long id) {
        ClientEntity entity = repository.findById(id)
          .orElseThrow(ClientNotFoundException::new);

        repository.delete(entity);
    }
}
