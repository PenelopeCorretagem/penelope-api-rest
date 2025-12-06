package penelope.corretagem.penelopeapirest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import penelope.corretagem.penelopeapirest.data.domain.dto.AddressRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO.AdvertisementCreateRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO.EstateCreateRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeCalResponse;
import penelope.corretagem.penelopeapirest.data.domain.entity.*;
import penelope.corretagem.penelopeapirest.data.domain.repository.*;
import penelope.corretagem.penelopeapirest.mapper.AddressMapper;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementComposeServiceTest {

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private EstateRepository estateRepository;

  @Mock
  private CloudinaryService cloudinaryService;

  @Mock
  private AddressMapper addressMapper;

  @Mock
  private ImageEstateRepository imageEstateRepository;

  @Mock
  private AmenitiesEstateRepository amenitiesEstateRepository;

  @Mock
  private AdvertisementRepository advertisementRepository;

  @Mock
  private EventTypeService eventTypeService;

  @InjectMocks
  private AdvertisementComposerService service;

  @Test
  @DisplayName("Deve criar anúncio com sucesso")
  void createAdvertisement_shouldCreateSuccessfully() throws IOException {
    // Arrange
    EstateCreateRequest request = createEstateRequest();

    AddressEntity address = new AddressEntity();
    address.setId(1L);

    AdvertisementEntity advertisement = new AdvertisementEntity();
    advertisement.setId(1L);

    when(addressMapper.toEntity(any(AddressRequest.class))).thenReturn(address);
    when(addressRepository.save(any(AddressEntity.class))).thenReturn(address);
    when(estateRepository.getLastInsertId()).thenReturn(1L);
    when(advertisementRepository.getLastInsertId()).thenReturn(1L);
    doNothing().when(imageEstateRepository).insertImageNative(anyLong(), anyInt(), anyString());
    doNothing().when(amenitiesEstateRepository).insertFeatureNative(anyLong(), anyLong());
    when(eventTypeService.createEventTypeForEstate(anyLong()))
      .thenReturn(new EventTypeCalResponse(
        1L,
        request.title(),
        generateSlugFromTitle(request.title()),
        60,
        request.description(),
        false,
        120,
        OffsetDateTime.now(),
        OffsetDateTime.now()
      ));
    when(advertisementRepository.findById(1L)).thenReturn(Optional.of(advertisement));

    // Act
    Optional<AdvertisementEntity> result = service.createAdvertisement(request);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getId());
    verify(addressRepository).save(any(AddressEntity.class));
    verify(estateRepository).createEstateNative(
      anyString(), anyString(), anyDouble(), anyInt(),
      anyString(), anyLong(), any());
    verify(advertisementRepository).insertAdvertisementNative(
      anyLong(), anyLong(), anyLong(), anyBoolean(), any(), anyLong());
  }

  @Test
  @DisplayName("Deve fazer upload de múltiplos arquivos")
  void uploadImages_shouldUploadMultipleFiles() throws IOException {
    // Arrange
    MultipartFile file1 = mock(MultipartFile.class);
    MultipartFile file2 = mock(MultipartFile.class);
    List<MultipartFile> files = List.of(file1, file2);

    when(cloudinaryService.uploadImage(file1)).thenReturn("https://cloudinary.com/img1.jpg");
    when(cloudinaryService.uploadImage(file2)).thenReturn("https://cloudinary.com/img2.jpg");

    // Act
    List<String> urls = service.uploadImages(files);

    // Assert
    assertEquals(2, urls.size());
    assertEquals("https://cloudinary.com/img1.jpg", urls.get(0));
    assertEquals("https://cloudinary.com/img2.jpg", urls.get(1));
    verify(cloudinaryService, times(2)).uploadImage(any(MultipartFile.class));
  }

  @Test
  @DisplayName("Deve retornar lista vazia quando não há imagens")
  void uploadImages_shouldReturnEmptyListWhenNoImages() throws IOException {
    // Arrange
    List<MultipartFile> emptyList = List.of();

    // Act
    List<String> result = service.uploadImages(emptyList);

    // Assert
    assertTrue(result.isEmpty());
    verify(cloudinaryService, never()).uploadImage(any(MultipartFile.class));
  }

  @Test
  @DisplayName("Não deve recriar evento quando o título não é alterado")
  void updateAdvertisement_shouldNotRecreateEventWhenTitleUnchanged() throws IOException {
    // Arrange
    AddressEntity address = new AddressEntity();
    address.setId(1L);

    EstateEntity estate = new EstateEntity();
    estate.setId(1L);
    estate.setTitle("Same Title");
    estate.setAddress(address);
    estate.setStandAddress(null);

    EstateCreateRequest request = createUpdateRequestWithSameTitle();

    EventTypeEntity eventType = new EventTypeEntity();
    eventType.setId(1L);

    AdvertisementEntity advertisement = new AdvertisementEntity();
    advertisement.setId(1L);
    advertisement.setEventType(eventType);

    when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));
    when(advertisementRepository.findByEstateId(1L)).thenReturn(advertisement);
    doNothing().when(amenitiesEstateRepository).deleteAmenities(anyLong());
    doNothing().when(amenitiesEstateRepository).insertFeatureNative(anyLong(), anyLong());
    doNothing().when(imageEstateRepository).deleteImages(anyLong());
    doNothing().when(imageEstateRepository).insertImageNative(anyLong(), anyInt(), anyString());

    // Act
    AdvertisementEntity result = service.updateAdvertisement(1L, request);

    // Assert
    assertNotNull(result);
    verify(estateRepository).updateEstate(
      anyLong(), anyString(), anyString(), anyDouble(), anyInt(), anyString());
    verify(eventTypeService, never()).updateEventTypeForEstate(anyLong());
    verify(eventTypeService, never()).createEventTypeForEstate(anyLong());
  }

  @Test
  @DisplayName("Deve recriar evento quando o título é alterado")
  void updateAdvertisement_shouldRecreateEventWhenTitleChanges() throws IOException {
    // Arrange
    AddressEntity address = new AddressEntity();
    address.setId(1L);

    EstateEntity estate = new EstateEntity();
    estate.setId(1L);
    estate.setTitle("Old Title");
    estate.setAddress(address);

    EstateCreateRequest request = createUpdateRequestWithNewTitle();

    EventTypeEntity eventType = new EventTypeEntity();
    eventType.setId(1L);

    AdvertisementEntity advertisement = new AdvertisementEntity();
    advertisement.setId(1L);
    advertisement.setEventType(eventType);

    when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));
    when(advertisementRepository.findByEstateId(1L)).thenReturn(advertisement);
    doNothing().when(amenitiesEstateRepository).deleteAmenities(anyLong());
    doNothing().when(amenitiesEstateRepository).insertFeatureNative(anyLong(), anyLong());
    doNothing().when(imageEstateRepository).deleteImages(anyLong());
    doNothing().when(imageEstateRepository).insertImageNative(anyLong(), anyInt(), anyString());
    when(eventTypeService.createEventTypeForEstate(anyLong()))
      .thenReturn(new EventTypeCalResponse(
        1L,
        request.title(),
        generateSlugFromTitle(request.title()),
        60,
        request.description(),
        false,
        120,
        OffsetDateTime.now(),
        OffsetDateTime.now()
      ));

    // Act
    AdvertisementEntity result = service.updateAdvertisement(1L, request);

    // Assert
    assertNotNull(result);
    verify(eventTypeService, times(1)).updateEventTypeForEstate(1L);
    verify(eventTypeService, times(1)).createEventTypeForEstate(1L);
  }

  @Test
  @DisplayName("Deve ativar anúncio")
  void updateAdvertisementStatus_shouldActivateAdvertisement() {
    // Arrange
    Long id = 1L;
    when(advertisementRepository.existsById(id)).thenReturn(true);

    // Act
    service.updateAdvertisementStatus(id, true);

    // Assert
    verify(advertisementRepository).updateActive(id, true);
  }

  @Test
  @DisplayName("Deve desativar anúncio")
  void updateAdvertisementStatus_shouldDeactivateAdvertisement() {
    // Arrange
    Long id = 1L;
    when(advertisementRepository.existsById(id)).thenReturn(true);

    // Act
    service.updateAdvertisementStatus(id, false);

    // Assert
    verify(advertisementRepository).updateActive(id, false);
  }

  @Test
  @DisplayName("Deve lançar exceção quando anúncio não é encontrado")
  void updateAdvertisementStatus_shouldThrowExceptionWhenAdvertisementNotFound() {
    // Arrange
    Long id = 999L;
    when(advertisementRepository.existsById(id)).thenReturn(false);

    // Act
    RuntimeException exception = assertThrows(
      RuntimeException.class,
      () -> service.updateAdvertisementStatus(id, true)
    );

    // Assert
    assertEquals("Anúncio não encontrado", exception.getMessage());
    verify(advertisementRepository, never()).updateActive(anyLong(), anyBoolean());
  }

  private EstateCreateRequest createEstateRequest() {
    AddressRequest addressRequest = new AddressRequest(
      null, "Rua Teste", "123", "Centro",
      "São Paulo", "SP", "01234-567", null, "Zona Sul"
    );

    AdvertisementCreateRequest advertisementRequest = new AdvertisementCreateRequest(
      1L, 2L, new Date(), true
    );

    return new EstateCreateRequest(
      "Apartamento Novo",
      "Descrição",
      100.0,
      3,
      "APARTAMENTO",
      advertisementRequest,
      addressRequest,
      null,
      List.of(1L, 2L),
      List.of("http://image.jpg"),
      List.of(1)
    );
  }

  private EstateCreateRequest createUpdateRequestWithSameTitle() {
    AddressRequest addressRequest = new AddressRequest(
      1L, "Rua Nova", "456", "Bairro",
      "São Paulo", "SP", "01234-567", null, "Zona Sul"
    );

    AdvertisementCreateRequest advertisementRequest = new AdvertisementCreateRequest(
      1L, 2L, new Date(), true
    );

    return new EstateCreateRequest(
      "Same Title",
      "Nova descrição",
      120.0,
      3,
      "APARTAMENTO",
      advertisementRequest,
      addressRequest,
      null,
      List.of(1L),
      List.of("http://image.jpg"),
      List.of(1)
    );
  }

  private EstateCreateRequest createUpdateRequestWithNewTitle() {
    AddressRequest addressRequest = new AddressRequest(
      1L, "Rua", "123", "Bairro",
      "São Paulo", "SP", "01234-567", null, "Zona"
    );

    AdvertisementCreateRequest advertisementRequest = new AdvertisementCreateRequest(
      1L, 2L, new Date(), true
    );

    return new EstateCreateRequest(
      "New Title",
      "Descrição",
      100.0,
      3,
      "APARTAMENTO",
      advertisementRequest,
      addressRequest,
      null,
      List.of(1L),
      List.of("https://image.jpg"),
      List.of(1)
    );
  }

  private String generateSlugFromTitle(String title) {
    return title.toLowerCase()
      .replaceAll("[^a-z0-9\\s-]", "")
      .replaceAll("\\s+", "-")
      .replaceAll("-+", "-")
      .replaceAll("^-|-$", "");
  }
}