package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO.EstateCreateRequest;
import penelope.corretagem.penelopeapirest.data.domain.entity.*;
import penelope.corretagem.penelopeapirest.data.domain.repository.*;
import penelope.corretagem.penelopeapirest.mapper.AddressMapper;

import java.io.IOException;
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


//        if (files != null && !files.isEmpty()) {
//            for (int i = 0; i < files.size(); i++) {
//                MultipartFile file = files.get(i);
//                String tipoImagem = imageTypes.get(i);
//                int idTipoImagem = 0;
//
//                String uploadedUrl = cloudinaryService.uploadImage(file);
//
//                if (tipoImagem.equals("capa")){
//                    idTipoImagem = 0;
//                }
//                else if(tipoImagem.equals("galeria")){
//                    idTipoImagem = 1;
//                }
//                else if (tipoImagem.equals("planta")){
//                    idTipoImagem = 2;
//                }
//
//                imageEstateRepository.insertImageNative(idEstate, idTipoImagem, uploadedUrl);
//            }
//        }

//        for (int i = 0; i < estateCreateRequest.amenitiesIds().size(); i++) {
//            Long featureId = estateCreateRequest.amenitiesIds().get(i);
//            amenitiesEstateRepository.insertFeatureNative(idEstate, featureId);
//        }

        advertisementRepository.insertAdvertisementNative(idEstate,
                estateCreateRequest.advertisementCreateRequest().creator(),
                estateCreateRequest.advertisementCreateRequest().responsible(),
                estateCreateRequest.advertisementCreateRequest().active(),
                estateCreateRequest.advertisementCreateRequest().dataFim());

        Long advertisementId = advertisementRepository.getLastInsertId();

        return advertisementRepository.findById(advertisementId);
    }
}
