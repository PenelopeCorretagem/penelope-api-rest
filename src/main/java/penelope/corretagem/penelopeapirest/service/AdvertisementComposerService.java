package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO.EstateCreateRequest;
import penelope.corretagem.penelopeapirest.data.domain.entity.*;
import penelope.corretagem.penelopeapirest.data.domain.repository.*;
import penelope.corretagem.penelopeapirest.mapper.AddressMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class AdvertisementComposerService {
    private final AddressRepository addressRepository;
    private final EstateRepository estateRepository;
    private final CloudinaryService cloudinaryService;
    private final AddressMapper addressMapper;
    private final ImageEstateRepository imageEstateRepository;
    private final AmenitiesEstateRepository amenitiesEstateRepository;
    private final AdvertisementRepository advertisementRepository;
    private final EventTypeService eventTypeService;

    public AdvertisementComposerService(AddressRepository addressRepository,
                                        EstateRepository estateRepository,
                                        CloudinaryService cloudinaryService,
                                        AddressMapper addressMapper,
                                        ImageEstateRepository imageEstateRepository,
                                        AmenitiesEstateRepository amenitiesEstateRepository,
                                        AdvertisementRepository advertisementRepository, EventTypeService eventTypeService) {
        this.addressRepository = addressRepository;
        this.estateRepository = estateRepository;
        this.cloudinaryService = cloudinaryService;
        this.addressMapper = addressMapper;
        this.imageEstateRepository = imageEstateRepository;
        this.amenitiesEstateRepository = amenitiesEstateRepository;
        this.advertisementRepository = advertisementRepository;
        this.eventTypeService = eventTypeService;
    }

    @Transactional
    public Optional<AdvertisementEntity> createAdvertisement(EstateCreateRequest estateCreateRequest
    ) throws IOException {

        var addressReq = estateCreateRequest.address();
        AddressEntity address = addressMapper.toEntity(addressReq);
        var savedAddress = addressRepository.save(address);

        AddressEntity savedStandAddress = null;
        if (estateCreateRequest.standAddress() != null) {
            savedStandAddress = addressRepository.save(addressMapper.toEntity(estateCreateRequest.standAddress()));
        }

        estateRepository.createEstateNative(
                estateCreateRequest.title(),
                estateCreateRequest.description(),
                estateCreateRequest.area(),
                estateCreateRequest.numberOfRooms(),
                estateCreateRequest.type(),
                savedAddress.getId(),
                savedStandAddress != null ? savedStandAddress.getId() : null);

        Long idEstate = estateRepository.getLastInsertId();


        if (estateCreateRequest.images() != null && !estateCreateRequest.images().isEmpty()) {
            for (int i = 0; i < estateCreateRequest.images().size(); i++) {
                String url = estateCreateRequest.images().get(i);
                Integer imageType = estateCreateRequest.imageType().get(i);

                imageEstateRepository.insertImageNative(idEstate, imageType, url);
            }
        }

        for (int i = 0; i < estateCreateRequest.amenitiesIds().size(); i++) {
            Long featureId = estateCreateRequest.amenitiesIds().get(i);
            amenitiesEstateRepository.insertFeatureNative(idEstate, featureId);
        }

        var eventType = eventTypeService.createEventTypeForEstate(idEstate);
        Long eventTypeId = eventType.id();

        advertisementRepository.insertAdvertisementNative(idEstate,
                estateCreateRequest.advertisementCreateRequest().creator(),
                estateCreateRequest.advertisementCreateRequest().responsible(),
                estateCreateRequest.advertisementCreateRequest().active(),
                estateCreateRequest.advertisementCreateRequest().dataFim(),
                eventTypeId);

        Long advertisementId = advertisementRepository.getLastInsertId();

        return advertisementRepository.findById(advertisementId);
    }


    @Transactional
    public List<String> uploadImages(List<MultipartFile> files) throws IOException {
        List<String> result = new ArrayList<>();

        for (MultipartFile file : files) {
            String url = cloudinaryService.uploadImage(file);
            result.add(url);
        }
        return result;
    }

    @Transactional
    public AdvertisementEntity updateAdvertisement(Long estateId, EstateCreateRequest req) throws IOException {

        var estate = estateRepository.findById(estateId)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrado"));

        boolean shouldUpdateEventType = hasEventTypeRelevantChanges(estate, req);

        updateMainAddress(estate, req);
        updateStandAddress(estate, req);

        updateEstateBaseData(estateId, req);
        updateAmenities(estateId, req);
        updateImages(estateId, req);

        if (shouldUpdateEventType) {
            eventTypeService.updateEventTypeForEstate(estateId);
            eventTypeService.createEventTypeForEstate(estateId);
        }

        AdvertisementEntity advertisement = advertisementRepository.findByEstateId(estateId);
        updateAdvertisementInfo(estateId, req, advertisement.getEventType().getId());

        return advertisementRepository.findByEstateId(estateId);
    }

    private boolean hasEventTypeRelevantChanges(EstateEntity estate, EstateCreateRequest req) {

        // Mudança no título
        if (!estate.getTitle().equals(req.title())) {
            return true;
        }

        var oldStand = estate.getStandAddress();
        var newStand = req.standAddress();

        if (oldStand == null && newStand == null) return false;

        if (oldStand == null || newStand == null) return true;

        return !oldStand.getStreet().equals(newStand.street()) ||
                !oldStand.getNumber().equals(newStand.number()) ||
                !oldStand.getNeighborhood().equals(newStand.neighborhood()) ||
                !oldStand.getCity().equals(newStand.city()) ||
                !oldStand.getUf().equals(newStand.uf()) ||
                !oldStand.getZipCode().equals(newStand.zipCode()) ||
                !Objects.equals(oldStand.getComplement(), newStand.complement()) ||
                !oldStand.getRegion().equals(newStand.region());
    }


    private void updateMainAddress(EstateEntity estate, EstateCreateRequest req) {

        var addr = req.address();

        addressRepository.updateAddress(
                estate.getAddress().getId(),
                addr.street(),
                addr.number(),
                addr.neighborhood(),
                addr.city(),
                addr.uf(),
                addr.zipCode(),
                addr.complement(),
                addr.region()
        );
    }

    private void updateStandAddress(EstateEntity estate, EstateCreateRequest req) {

        var oldStand = estate.getStandAddress();
        var newStandReq = req.standAddress();

        if (oldStand != null && newStandReq != null) {

            addressRepository.updateAddress(
                    oldStand.getId(),
                    newStandReq.street(),
                    newStandReq.number(),
                    newStandReq.neighborhood(),
                    newStandReq.city(),
                    newStandReq.uf(),
                    newStandReq.zipCode(),
                    newStandReq.complement(),
                    newStandReq.region()
            );
        }

        else if (oldStand == null && newStandReq != null) {

            var saved = addressRepository.save(addressMapper.toEntity(newStandReq));
            estateRepository.updateEstateStandAddressId(estate.getId(), saved.getId());
        }

        else if (oldStand != null && newStandReq == null) {

            Long standId = oldStand.getId();
            estateRepository.updateEstateStandAddressId(estate.getId(), null);
            addressRepository.deleteById(standId);
        }
    }

    private void updateEstateBaseData(Long estateId, EstateCreateRequest req) {
        estateRepository.updateEstate(
                estateId,
                req.title(),
                req.description(),
                req.area(),
                req.numberOfRooms(),
                req.type()
        );
    }

    private void updateAmenities(Long estateId, EstateCreateRequest req) {
        amenitiesEstateRepository.deleteAmenities(estateId);

        for (Long featureId : req.amenitiesIds()) {
            amenitiesEstateRepository.insertFeatureNative(estateId, featureId);
        }
    }

    private void updateImages(Long estateId, EstateCreateRequest req) {
        imageEstateRepository.deleteImages(estateId);

        for (int i = 0; i < req.images().size(); i++) {
            imageEstateRepository.insertImageNative(
                    estateId,
                    req.imageType().get(i),
                    req.images().get(i)
            );
        }
    }

    private void updateAdvertisementInfo(Long estateId, EstateCreateRequest req, Long eventTypeId) {
        var ad = req.advertisementCreateRequest();

        advertisementRepository.updateAdvertisement(
                estateId,
                ad.creator(),
                ad.responsible(),
                ad.active(),
                ad.dataFim(),
                eventTypeId
        );
    }

    @Transactional
    public void updateAdvertisementStatus(Long advertisementId, Boolean active) {

        if (!advertisementRepository.existsById(advertisementId))
            throw new RuntimeException("Anúncio não encontrado");

        advertisementRepository.updateActive(advertisementId, active);
    }
}
