package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import penelope.corretagem.penelopeapirest.clients.CalClient;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingFilterRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingListResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingUpdateRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeCalResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EventTypeEntity;
import penelope.corretagem.penelopeapirest.data.domain.repository.AdvertisementRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.EstateRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.EventTypeRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EventTypeService {

    private static final Logger logger = LoggerFactory.getLogger(EventTypeService.class);

    private final CalClient calClient;
    private final EstateRepository estateRepository;
    private final EventTypeRepository eventTypeRepository;
    private final AdvertisementRepository advertisementRepository;

    public EventTypeService(CalClient calClient, EstateRepository estateRepository, EventTypeRepository eventTypeRepository, AdvertisementRepository advertisementRepository) {
        this.calClient = calClient;
        this.estateRepository = estateRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.advertisementRepository = advertisementRepository;
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
            EventTypeEntity eventType = eventTypeRepository.save(new EventTypeEntity(
                    response.id(),
                    response.title(),
                    response.slug()
            ));
            logger.info("Event Type criado com sucesso. ID: {} para imóvel: {}", response.id(), estateId);

              AdvertisementEntity advertisement = advertisementRepository.findByEstateId(estateId);
                if(advertisement != null){
                    advertisement.setEventType(eventType);
                    advertisementRepository.save(advertisement);
                }
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

      AdvertisementEntity advertisement =  advertisementRepository.findByEstateId(estateId);

      EstateEntity estate = estateRepository.findById(estateId)
        .orElseThrow(() -> new RuntimeException("Imóvel não encontrado: " + estateId));

      EventTypeEntity eventType = advertisement.getEventType();

      if (eventType == null) {
        throw new RuntimeException("Anuncio não possui Event Type associado");
      }

      EventTypeRequest request = new EventTypeRequest(
        eventType.getTitle(),
        eventType.getSlug(),
        60, // manter duração padrão
        estate.getDescription(),
        true,
        120,
        false
      );

        try {
            EventTypeCalResponse response = calClient.updateEventType(eventType.getId(), request);

            return response;
        } catch (Exception e) {
            logger.error("Erro ao atualizar o Event Type para o imóvel {}: {}", estateId, e.getMessage(), e);
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

      AdvertisementEntity advertisement =  advertisementRepository.findByEstateId(estateId);
      EventTypeEntity eventType = advertisement.getEventType();

      if (eventType == null) {
        logger.warn("Anuncio {} não possui Event Type associado para deletar", estateId);
        return;
      }

      try {
        calClient.deleteEventType(eventType.getId());

        eventType.setId(null);
        advertisementRepository.save(advertisement);

        logger.info("Event Type {} deletado com sucesso para o anúncio {}",
         eventType.getId(), estateId);

      } catch (Exception e) {
        logger.error("Erro ao deletar Event Type {} do imóvel {}: {}",
          eventType.getId(), estateId, e.getMessage(), e);
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