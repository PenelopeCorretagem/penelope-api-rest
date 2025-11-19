package penelope.corretagem.penelopeapirest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.clients.CalClient;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeCalResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EventTypeEntity;
import penelope.corretagem.penelopeapirest.data.domain.repository.EstateRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.EventTypeRepository;

import java.util.List;

@Service
public class EventTypeService {

    private static final Logger logger = LoggerFactory.getLogger(EventTypeService.class);

    private final CalClient calClient;
    private final EstateRepository estateRepository;
    private final EventTypeRepository eventTypeRepository;

    public EventTypeService(CalClient calClient, EstateRepository estateRepository, EventTypeRepository eventTypeRepository) {
        this.calClient = calClient;
        this.estateRepository = estateRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    /**
     * Cria um Event Type no Cal.com quando uma nova localização (Estate) é criada
     */
    public EventTypeCalResponse createEventTypeForEstate(Long estateId) {
      logger.info("Criando Event Type para o imóvel ID: {}", estateId);

      EstateEntity estate = estateRepository.findById(estateId)
        .orElseThrow(() -> new RuntimeException("Imóvel não encontrado: " + estateId));

      EventTypeRequest request = new EventTypeRequest(
        estate.getTitle(),
        generateSlugFromTitle(estate.getTitle()),
        60,
        estate.getDescription(),
        false,
        120,
        false
        );

        try {
          EventTypeCalResponse response = calClient.createEventType(request);

          if (response != null) {
            eventTypeRepository.save(new EventTypeEntity(
                    response.id(),
                    response.title(),
                    response.slug()
            ));
            logger.info("Event Type criado com sucesso. ID: {} para imóvel: {}", response.id(), estateId);
          }

          return response;
        } catch (Exception e) {
            logger.error("Erro ao criar Event Type para o imóvel {}: {}", estateId, e.getMessage(), e);
            throw new RuntimeException("Falha ao criar Event Type: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza um Event Type existente quando os dados do imóvel mudam
     */
    public EventTypeCalResponse updateEventTypeForEstate(Long estateId) {
      logger.info("Atualizando Event Type para o imóvel ID: {}", estateId);

      EstateEntity estate = estateRepository.findById(estateId)
        .orElseThrow(() -> new RuntimeException("Imóvel não encontrado: " + estateId));

      if (estate.getCalEventTypeId() == null) {
        throw new RuntimeException("Imóvel não possui Event Type associado");
      }

      EventTypeRequest request = new EventTypeRequest(
        estate.getTitle(),
        generateSlugFromTitle(estate.getTitle()),
        60, // manter duração padrão
        estate.getDescription(),
        false,
        120,
        false
      );

      try {
        return calClient.updateEventType(estate.getCalEventTypeId(), request);

      } catch (Exception e) {
        logger.error("Erro ao atualizar Event Type {} do imóvel {}: {}",
          estate.getCalEventTypeId(), estateId, e.getMessage(), e);
        throw new RuntimeException("Falha ao atualizar Event Type: " + e.getMessage(), e);
      }
    }

    /**
     * Lista todos os Event Types
     */
    public List<EventTypeCalResponse> listAllEventTypes() {
      logger.info("Listando todos os Event Types");

      try {
        return calClient.listEventTypes(calClient.getAuthenticatedUser().username());

      } catch (Exception e) {
        logger.error("Erro ao listar Event Types: {}", e.getMessage(), e);
        throw new RuntimeException("Falha ao listar Event Types: " + e.getMessage(), e);
      }
    }

    /**
     * Busca um Event Type específico
     */
    public EventTypeCalResponse getEventType(Long eventTypeId) {
      logger.info("Buscando Event Type ID: {}", eventTypeId);

      try {
        return calClient.getEventType(eventTypeId);

      } catch (Exception e) {
          logger.error("Erro ao buscar Event Type {}: {}", eventTypeId, e.getMessage(), e);
          throw new RuntimeException("Falha ao buscar Event Type: " + e.getMessage(), e);
      }
    }

    /**
     * Deleta um Event Type quando um imóvel é removido/desativado
     */
    public void deleteEventTypeForEstate(Long estateId) {
      logger.info("Deletando Event Type para o imóvel ID: {}", estateId);

      EstateEntity estate = estateRepository.findById(estateId)
        .orElseThrow(() -> new RuntimeException("Imóvel não encontrado: " + estateId));

      if (estate.getCalEventTypeId() == null) {
        logger.warn("Imóvel {} não possui Event Type associado para deletar", estateId);
        return;
      }

      try {
        calClient.deleteEventType(estate.getCalEventTypeId());

        estate.setCalEventTypeId(null);
        estateRepository.save(estate);

        logger.info("Event Type {} deletado com sucesso para o imóvel {}",
          estate.getCalEventTypeId(), estateId);

      } catch (Exception e) {
        logger.error("Erro ao deletar Event Type {} do imóvel {}: {}",
          estate.getCalEventTypeId(), estateId, e.getMessage(), e);
        throw new RuntimeException("Falha ao deletar Event Type: " + e.getMessage(), e);
      }
    }

    /**
     * Gera um slug único baseado no título
     */
    private String generateSlugFromTitle(String title) {
      return title.toLowerCase()
        .replaceAll("[^a-z0-9\\s-]", "") // remove caracteres especiais
        .replaceAll("\\s+", "-") // substitui espaços por hífens
        .replaceAll("-+", "-") // remove hífens duplicados
        .replaceAll("^-|-$", ""); // remove hífens do início e fim
    }
}