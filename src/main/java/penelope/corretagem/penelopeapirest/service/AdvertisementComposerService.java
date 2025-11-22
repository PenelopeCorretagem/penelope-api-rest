package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;
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

    public AdvertisementComposerService(AddressRepository addressRepository,
                                        EstateRepository estateRepository,
                                        CloudinaryService cloudinaryService,
                                        AddressMapper addressMapper,
                                        ImageEstateRepository imageEstateRepository,
                                        AmenitiesEstateRepository amenitiesEstateRepository,
                                        AdvertisementRepository advertisementRepository) {
        this.addressRepository = addressRepository;
        this.estateRepository = estateRepository;
        this.cloudinaryService = cloudinaryService;
        this.addressMapper = addressMapper;
        this.imageEstateRepository = imageEstateRepository;
        this.amenitiesEstateRepository = amenitiesEstateRepository;
        this.advertisementRepository = advertisementRepository;
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

        advertisementRepository.insertAdvertisementNative(idEstate,
                estateCreateRequest.advertisementCreateRequest().creator(),
                estateCreateRequest.advertisementCreateRequest().responsible(),
                estateCreateRequest.advertisementCreateRequest().active(),
                estateCreateRequest.advertisementCreateRequest().dataFim());

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
    public Optional<AdvertisementEntity> updateAdvertisement(Long estateId, EstateCreateRequest estateCreateRequest
    ) throws IOException {
            var estate = estateRepository.findById(estateId)
                    .orElseThrow(() -> new RuntimeException("Propriedade não encontrado"));

            addressRepository.updateAddress(
                    estate.getAddress().getId(),
                    estateCreateRequest.address().street(),
                    estateCreateRequest.address().number(),
                    estateCreateRequest.address().neighborhood(),
                    estateCreateRequest.address().city(),
                    estateCreateRequest.address().uf(),
                    estateCreateRequest.address().zipCode(),
                    estateCreateRequest.address().complement(),
                    estateCreateRequest.address().region()
            );


            AddressEntity oldStandAddress = estate.getStandAddress();
            var newStandReq = estateCreateRequest.standAddress();

            if (oldStandAddress != null && newStandReq != null) {

                addressRepository.updateAddress(
                        oldStandAddress.getId(),
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

            else if (oldStandAddress == null && newStandReq != null) {

                var newStandAddress = addressMapper.toEntity(newStandReq);
                var savedStand = addressRepository.save(newStandAddress);

                estateRepository.updateEstateStandAddressId(
                        estateId,
                        savedStand.getId()
                );
            }

            else if (oldStandAddress != null && newStandReq == null) {

                Long standId = oldStandAddress.getId();

                estateRepository.updateEstateStandAddressId(estateId, null);

                addressRepository.deleteById(standId);
            }

            estateRepository.updateEstate(
                    estateId,
                    estateCreateRequest.title(),
                    estateCreateRequest.description(),
                    estateCreateRequest.area(),
                    estateCreateRequest.numberOfRooms(),
                    estateCreateRequest.type()
            );

            amenitiesEstateRepository.deleteAmenities(estateId);
            for (Long featureId : estateCreateRequest.amenitiesIds()) {
                amenitiesEstateRepository.insertFeatureNative(estateId, featureId);
            }

            imageEstateRepository.deleteImages(estateId);

            for (int i = 0; i < estateCreateRequest.images().size(); i++) {
                imageEstateRepository.insertImageNative(
                        estateId,
                        estateCreateRequest.imageType().get(i),
                        estateCreateRequest.images().get(i)
                );
            }

            advertisementRepository.updateAdvertisement(
                    estateId,
                    estateCreateRequest.advertisementCreateRequest().creator(),
                    estateCreateRequest.advertisementCreateRequest().responsible(),
                    estateCreateRequest.advertisementCreateRequest().active(),
                    estateCreateRequest.advertisementCreateRequest().dataFim()
            );

            return advertisementRepository.findByEstateId(estateId);
        }

        @Transactional
        public void deactivateAdvertisement(Long advertisementId, Boolean active) {

            if (!advertisementRepository.existsById(advertisementId))
                throw new RuntimeException("Anúncio não encontrado");

            advertisementRepository.updateActive(advertisementId, active);
        }
}
