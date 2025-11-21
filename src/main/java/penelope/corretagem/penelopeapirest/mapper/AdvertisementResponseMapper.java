package penelope.corretagem.penelopeapirest.mapper;

import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.*;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.AmenitiesEntity;

import java.util.stream.Collectors;

public class AdvertisementResponseMapper {

    public static AdvertisementResponse toDTO(AdvertisementEntity anuncio) {
        var empreendimento = anuncio.getProperty();

        // Endereço principal
        var addressDto = new AddressResponse(
                empreendimento.getAddress().getId(),
                empreendimento.getAddress().getStreet(),
                empreendimento.getAddress().getNumber(),
                empreendimento.getAddress().getNeighborhood(),
                empreendimento.getAddress().getCity(),
                empreendimento.getAddress().getUf(),
                empreendimento.getAddress().getRegion(),
                empreendimento.getAddress().getZipCode(),
                empreendimento.getAddress().getComplement()
        );

        // Endereço stand
        var stand = empreendimento.getStandAddress();
        var standAddressDto = (stand == null)
                ? new StandAddressResponse(null, null, null, null, null, null, null, null, null)
                : new StandAddressResponse(
                stand.getId(),
                stand.getStreet(),
                stand.getNumber(),
                stand.getNeighborhood(),
                stand.getCity(),
                stand.getUf(),
                stand.getRegion(),
                stand.getZipCode(),
                stand.getComplement()
        );

        // Imagens
        var imagesDto = empreendimento.getImages().stream()
                .map(img -> new ImagesResponse(img.getId(), img.getUrl(), img.getType().getDescription()))
                .collect(Collectors.toSet());

        // Diferenciais
        var amenitiesDto = empreendimento.getAmenities().stream()
                .map(amenityRelation -> amenityRelation.getAmenity()) // pega o AmenitiesEntity real
                .map(a -> new AmenitiesResponse(a.getId(), a.getDescription()))
                .collect(Collectors.toSet());

        // Criador e responsável
        var creatorDto = anuncio.getCreator() != null ?
                new CreatorResponse(
                        anuncio.getCreator().getId(),
                        anuncio.getCreator().getName(),
                        anuncio.getCreator().getEmail(),
                        anuncio.getCreator().getPhone()
                ) : null;

        var responsibleDto = anuncio.getResponsible() != null ?
                new ResponsibleResponse(
                        anuncio.getResponsible().getId(),
                        anuncio.getResponsible().getName(),
                        anuncio.getResponsible().getEmail(),
                        anuncio.getResponsible().getPhone()
                ) : null;

        // Empreendimento
        var estateDto = new EstateResponse(
                empreendimento.getId(),
                empreendimento.getTitle(),
                empreendimento.getDescription(),
                empreendimento.getArea(),
                empreendimento.getNumberOfRooms(),
                empreendimento.getType().name(),
                addressDto,
                standAddressDto,
                imagesDto,
                amenitiesDto
        );

        // Retorno do anúncio
        return new AdvertisementResponse(
                anuncio.getId(),
                anuncio.getActive(),
                anuncio.getEmphasis(),
                anuncio.getCreatedAt(),
                anuncio.getEndDate() != null ? anuncio.getEndDate() : null,
                creatorDto,
                responsibleDto,
                estateDto
        );
    }
}
