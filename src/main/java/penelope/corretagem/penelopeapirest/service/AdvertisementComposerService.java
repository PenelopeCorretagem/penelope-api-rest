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
                                        /* CloudinaryService cloudinaryService, */
                                        AddressMapper addressMapper,
                                        ImageEstateRepository imageEstateRepository,
                                        AmenitiesEstateRepository amenitiesEstateRepository,
                                        AdvertisementRepository advertisementRepository, EventTypeService eventTypeService) {
        this.addressRepository = addressRepository;
        this.estateRepository = estateRepository;
        /* this.cloudinaryService = cloudinaryService; */
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

        var standAddressReq = estateCreateRequest.standAddress();
        AddressEntity standAddress = addressMapper.toEntity(standAddressReq);
        var savedStandAddress = addressRepository.save(standAddress);

        estateRepository.createEstateNative(
                estateCreateRequest.title(),
                estateCreateRequest.description(),
                estateCreateRequest.area(),
                estateCreateRequest.numberOfRooms(),
                estateCreateRequest.type(),
                savedAddress.getId(),
                savedStandAddress.getId());

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
}
