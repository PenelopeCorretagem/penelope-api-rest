//package penelope.corretagem.penelopeapirest.service;
//
//import org.springframework.stereotype.Service;
//import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;
//import penelope.corretagem.penelopeapirest.data.domain.repository.AdvertisementRepository;
//import penelope.corretagem.penelopeapirest.mapper.AdvertisementResponseMapper;
//
//
//@Service
//public class AdvertisementService {
//
//    private final AdvertisementRepository repository;
//
//    public AdvertisementService(AdvertisementRepository repository) {
//        this.repository = repository;
//    }
//
////
////    public List<AdvertisementResponse> getAllAdvertisements(AdvertisementFilterRequest request) {
////
////        Estate.Type type = null;
////        if (request.type() != null) {
////            type = Estate.Type.valueOf(request.type().toUpperCase());
////        }
////
////        Specification<AdvertisementEntity> spec =
////                AdvertisementSpecifications.hasCidade(request.city())
////                        .and(AdvertisementSpecifications.hasRegiao(request.region()))
////                        .and(AdvertisementSpecifications.hasTipo(type))
////                        .and(AdvertisementSpecifications.hasQuartos(request.numberOfRooms()))
////                        .and(AdvertisementSpecifications.hasArea(request.area()))
////                        .and(AdvertisementSpecifications.hasTitulo(request.title()))
////                        .and(AdvertisementSpecifications.hasDescricao(request.description()))
////                        .and(AdvertisementSpecifications.isActive(request.active()))
////                        .and(AdvertisementSpecifications.createdAtEquals(request.createdAt()))
////                        .and(AdvertisementSpecifications.createdAtGreaterThan(request.createdAtMin()))
////                        .and(AdvertisementSpecifications.createdAtLessThan(request.createdAtMax()))
////                        .and(AdvertisementSpecifications.endDateEquals(request.endDate()))
////                        .and(AdvertisementSpecifications.endDateGreaterThan(request.endDateMin()))
////                        .and(AdvertisementSpecifications.endDateLessThan(request.endDateMax()));
////
////        var anuncios = repository.findAll(spec);
////
////        return anuncios.stream()
////                .map(AdvertisementResponseMapper::toDTO)
////                .collect(Collectors.toList());
////    }
////
////
////    public AdvertisementResponse getLatestAdvertisement() {
////        return repository.findTopByOrderByCreatedAtDesc()
////                .map(AdvertisementResponseMapper::toDTO)
////                .orElse(null);
////    }
////
////    public AdvertisementResponse getAdvertisementById(Long id) {
////        return repository.findByIdWithAllRelations(id)
////                .map(AdvertisementResponseMapper::toDTO)
////                .orElse(null);
////    }
//
//}