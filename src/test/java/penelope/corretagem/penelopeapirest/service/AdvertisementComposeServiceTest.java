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
    AddressEntity address = createAddress(1L);
    AdvertisementEntity advertisement = createAdvertisement(createEventType());

    when(addressMapper.toEntity(any(AddressRequest.class))).thenReturn(address);
    when(addressRepository.save(any(AddressEntity.class))).thenReturn(address);
    setupCommonCreateMocks(request);
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
  @DisplayName("Deve criar anúncio com endereço do stand")
  void createAdvertisement_shouldCreateWithStandAddress() throws IOException {
    // Arrange
    EstateCreateRequest request = createEstateRequestWithStandAddress();
    AddressEntity address = createAddress(1L);
    AddressEntity standAddress = createAddress(2L);
    AdvertisementEntity advertisement = createAdvertisement(createEventType());

    when(addressMapper.toEntity(any(AddressRequest.class)))
      .thenReturn(address)
      .thenReturn(standAddress);
    when(addressRepository.save(any(AddressEntity.class)))
      .thenReturn(address)
      .thenReturn(standAddress);
    setupCommonCreateMocks(request);
    when(advertisementRepository.findById(1L)).thenReturn(Optional.of(advertisement));

    // Act
    Optional<AdvertisementEntity> result = service.createAdvertisement(request);

    // Assert
    assertTrue(result.isPresent());
    verify(addressRepository, times(2)).save(any(AddressEntity.class));
    verify(estateRepository).createEstateNative(
      anyString(), anyString(), anyDouble(), anyInt(),
      anyString(), anyLong(), eq(2L));
  }

  @Test
  @DisplayName("Deve criar anúncio sem imagens")
  void createAdvertisement_shouldCreateWithoutImages() throws IOException {
    // Arrange
    EstateCreateRequest request = createEstateRequestWithoutImages();
    AddressEntity address = createAddress(1L);
    AdvertisementEntity advertisement = createAdvertisement(createEventType());

    when(addressMapper.toEntity(any(AddressRequest.class))).thenReturn(address);
    when(addressRepository.save(any(AddressEntity.class))).thenReturn(address);
    setupCreateMocksWithoutImages(request);
    when(advertisementRepository.findById(1L)).thenReturn(Optional.of(advertisement));

    // Act
    Optional<AdvertisementEntity> result = service.createAdvertisement(request);

    // Assert
    assertTrue(result.isPresent());
    verify(imageEstateRepository, never()).insertImageNative(anyLong(), anyInt(), anyString());
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
  @DisplayName("Deve propagar exceção quando upload falha")
  void uploadImages_shouldPropagateExceptionWhenUploadFails() throws IOException {
    // Arrange
    MultipartFile file = mock(MultipartFile.class);
    List<MultipartFile> files = List.of(file);

    when(cloudinaryService.uploadImage(file)).thenThrow(new IOException("Upload failed"));

    // Act & Assert
    assertThrows(IOException.class, () -> service.uploadImages(files));
  }

  @Test
  @DisplayName("Não deve recriar evento quando o título não é alterado")
  void updateAdvertisement_shouldNotRecreateEventWhenTitleUnchanged() throws IOException {
    // Arrange
    AddressEntity address = createAddress(1L);
    EstateEntity estate = new EstateEntity();
    estate.setId(1L);
    estate.setTitle("Same Title");
    estate.setAddress(address);
    estate.setStandAddress(null);

    EstateCreateRequest request = createUpdateRequestWithSameTitle();
    EventTypeEntity eventType = createEventType();
    AdvertisementEntity advertisement = createAdvertisement(eventType);

    when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));
    when(advertisementRepository.findByEstateId(1L)).thenReturn(advertisement);
    setupUpdateMocksWithoutEventType();

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
  @DisplayName("Deve adicionar endereço do stand quando não existia")
  void updateAdvertisement_shouldAddStandAddressWhenNull() throws IOException {
    // Arrange
    AddressEntity mainAddress = createAddress(1L);
    EstateEntity estate = new EstateEntity();
    estate.setId(1L);
    estate.setTitle("Title");
    estate.setAddress(mainAddress);
    estate.setStandAddress(null);

    AddressEntity newStandAddress = createAddress(2L);
    EstateCreateRequest request = createUpdateRequestWithStandAddress();
    EventTypeEntity eventType = createEventType();
    AdvertisementEntity advertisement = createAdvertisement(eventType);

    when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));
    when(addressMapper.toEntity(any(AddressRequest.class))).thenReturn(newStandAddress);
    when(addressRepository.save(any(AddressEntity.class))).thenReturn(newStandAddress);
    when(advertisementRepository.findByEstateId(1L)).thenReturn(advertisement);
    setupCommonUpdateMocks(request);

    // Act
    AdvertisementEntity result = service.updateAdvertisement(1L, request);

    // Assert
    assertNotNull(result);
    verify(addressRepository).save(any(AddressEntity.class));
    verify(estateRepository).updateEstateStandAddressId(1L, 2L);
    verify(eventTypeService).updateEventTypeForEstate(1L);
    verify(eventTypeService).createEventTypeForEstate(1L);
  }

  @Test
  @DisplayName("Deve remover endereço do stand quando se torna null")
  void updateAdvertisement_shouldRemoveStandAddressWhenBecomesNull() throws IOException {
    // Arrange
    AddressEntity mainAddress = createAddress(1L);
    AddressEntity standAddress = createAddress(2L);
    EstateEntity estate = new EstateEntity();
    estate.setId(1L);
    estate.setTitle("Title");
    estate.setAddress(mainAddress);
    estate.setStandAddress(standAddress);

    EstateCreateRequest request = createUpdateRequestWithoutStandAddress();
    EventTypeEntity eventType = createEventType();
    AdvertisementEntity advertisement = createAdvertisement(eventType);

    when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));
    when(advertisementRepository.findByEstateId(1L)).thenReturn(advertisement);
    setupCommonUpdateMocks(request);

    // Act
    AdvertisementEntity result = service.updateAdvertisement(1L, request);

    // Assert
    assertNotNull(result);
    verify(estateRepository).updateEstateStandAddressId(1L, null);
    verify(addressRepository).deleteById(2L);
    verify(eventTypeService).updateEventTypeForEstate(1L);
    verify(eventTypeService).createEventTypeForEstate(1L);
  }

  @Test
  @DisplayName("Deve atualizar endereço do stand quando já existe")
  void updateAdvertisement_shouldUpdateExistingStandAddress() throws IOException {
    // Arrange
    EstateEntity estate = createEstateWithStandAddress();
    EstateCreateRequest request = createUpdateRequestWithDifferentStandAddress();
    EventTypeEntity eventType = createEventType();
    AdvertisementEntity advertisement = createAdvertisement(eventType);

    when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));
    when(advertisementRepository.findByEstateId(1L)).thenReturn(advertisement);
    setupCommonUpdateMocks(request);

    // Act
    AdvertisementEntity result = service.updateAdvertisement(1L, request);

    // Assert
    assertNotNull(result);
    verify(addressRepository).updateAddress(
      2L, "New Street", "200", "New Neighborhood",
      "New City", "RJ", "11111-111", "New", "New Region"
    );
    verify(eventTypeService).updateEventTypeForEstate(1L);
    verify(eventTypeService).createEventTypeForEstate(1L);
  }

  @Test
  @DisplayName("Deve recriar evento quando o título é alterado")
  void updateAdvertisement_shouldRecreateEventWhenTitleChanges() throws IOException {
    // Arrange
    AddressEntity address = createAddress(1L);
    EstateEntity estate = new EstateEntity();
    estate.setId(1L);
    estate.setTitle("Old Title");
    estate.setAddress(address);

    EstateCreateRequest request = createUpdateRequestWithNewTitle();
    EventTypeEntity eventType = createEventType();
    AdvertisementEntity advertisement = createAdvertisement(eventType);

    when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));
    when(advertisementRepository.findByEstateId(1L)).thenReturn(advertisement);
    setupCommonUpdateMocks(request);

    // Act
    AdvertisementEntity result = service.updateAdvertisement(1L, request);

    // Assert
    assertNotNull(result);
    verify(eventTypeService).updateEventTypeForEstate(1L);
    verify(eventTypeService).createEventTypeForEstate(1L);
  }

  @Test
  @DisplayName("Deve lançar exceção quando propriedade não é encontrada no update")
  void updateAdvertisement_shouldThrowExceptionWhenEstateNotFound() {
    // Arrange
    Long estateId = 999L;
    EstateCreateRequest request = createEstateRequest();

    when(estateRepository.findById(estateId)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(
      RuntimeException.class,
      () -> service.updateAdvertisement(estateId, request)
    );

    assertEquals("Propriedade não encontrado", exception.getMessage());
    verify(advertisementRepository, never()).findByEstateId(anyLong());
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

  // ==================== Utility Methods ====================

  private void setupCommonCreateMocks(EstateCreateRequest request) {
    when(estateRepository.getLastInsertId()).thenReturn(1L);
    when(advertisementRepository.getLastInsertId()).thenReturn(1L);
    doNothing().when(imageEstateRepository).insertImageNative(anyLong(), anyInt(), anyString());
    doNothing().when(amenitiesEstateRepository).insertFeatureNative(anyLong(), anyLong());
    when(eventTypeService.createEventTypeForEstate(anyLong()))
      .thenReturn(createEventTypeResponse(request));
  }

  private void setupCreateMocksWithoutImages(EstateCreateRequest request) {
    when(estateRepository.getLastInsertId()).thenReturn(1L);
    when(advertisementRepository.getLastInsertId()).thenReturn(1L);
    doNothing().when(amenitiesEstateRepository).insertFeatureNative(anyLong(), anyLong());
    when(eventTypeService.createEventTypeForEstate(anyLong()))
      .thenReturn(createEventTypeResponse(request));
  }

  private void setupCommonUpdateMocks(EstateCreateRequest request) {
    doNothing().when(amenitiesEstateRepository).deleteAmenities(anyLong());
    doNothing().when(amenitiesEstateRepository).insertFeatureNative(anyLong(), anyLong());
    doNothing().when(imageEstateRepository).deleteImages(anyLong());
    doNothing().when(imageEstateRepository).insertImageNative(anyLong(), anyInt(), anyString());
    when(eventTypeService.createEventTypeForEstate(anyLong()))
      .thenReturn(createEventTypeResponse(request));
  }

  private void setupUpdateMocksWithoutEventType() {
    doNothing().when(amenitiesEstateRepository).deleteAmenities(anyLong());
    doNothing().when(amenitiesEstateRepository).insertFeatureNative(anyLong(), anyLong());
    doNothing().when(imageEstateRepository).deleteImages(anyLong());
    doNothing().when(imageEstateRepository).insertImageNative(anyLong(), anyInt(), anyString());
  }

  private EventTypeCalResponse createEventTypeResponse(EstateCreateRequest request) {
    return new EventTypeCalResponse(
      1L,
      request.title(),
      generateSlugFromTitle(request.title()),
      60,
      request.description(),
      false,
      120,
      OffsetDateTime.now(),
      OffsetDateTime.now()
    );
  }

  private AddressEntity createAddress(Long id) {
    AddressEntity address = new AddressEntity();
    address.setId(id);
    return address;
  }

  private AdvertisementEntity createAdvertisement(EventTypeEntity eventType) {
    AdvertisementEntity advertisement = new AdvertisementEntity();
    advertisement.setId(1L);
    advertisement.setEventType(eventType);
    return advertisement;
  }

  private EventTypeEntity createEventType() {
    EventTypeEntity eventType = new EventTypeEntity();
    eventType.setId(1L);
    return eventType;
  }

  private EstateEntity createEstateWithStandAddress() {
    AddressEntity mainAddress = createAddress(1L);
    
    AddressEntity standAddress = new AddressEntity();
    standAddress.setId(2L);
    standAddress.setStreet("Old Street");
    standAddress.setNumber("100");
    standAddress.setNeighborhood("Old Neighborhood");
    standAddress.setCity("Old City");
    standAddress.setUf("SP");
    standAddress.setZipCode("00000-000");
    standAddress.setComplement("Old");
    standAddress.setRegion("Old Region");

    EstateEntity estate = new EstateEntity();
    estate.setId(1L);
    estate.setTitle("Title");
    estate.setAddress(mainAddress);
    estate.setStandAddress(standAddress);
    return estate;
  }

  private AdvertisementCreateRequest createAdvertisementRequest() {
    return new AdvertisementCreateRequest(1L, 2L, new Date(), true);
  }

  private AddressRequest createMainAddressRequest(Long id) {
    return new AddressRequest(
      id, "Rua Principal", "100", "Centro",
      "São Paulo", "SP", "01234-567", null, "Zona Sul"
    );
  }

  private EstateCreateRequest createEstateRequest() {
    return new EstateCreateRequest(
      "Apartamento Novo",
      "Descrição",
      100.0,
      3,
      "APARTAMENTO",
      createAdvertisementRequest(),
      new AddressRequest(null, "Rua Teste", "123", "Centro",
        "São Paulo", "SP", "01234-567", null, "Zona Sul"),
      null,
      List.of(1L, 2L),
      List.of("http://image.jpg"),
      List.of(1)
    );
  }

  private EstateCreateRequest createUpdateRequestWithSameTitle() {
    return new EstateCreateRequest(
      "Same Title",
      "Nova descrição",
      120.0,
      3,
      "APARTAMENTO",
      createAdvertisementRequest(),
      new AddressRequest(1L, "Rua Nova", "456", "Bairro",
        "São Paulo", "SP", "01234-567", null, "Zona Sul"),
      null,
      List.of(1L),
      List.of("http://image.jpg"),
      List.of(1)
    );
  }

  private EstateCreateRequest createUpdateRequestWithNewTitle() {
    return new EstateCreateRequest(
      "New Title",
      "Descrição",
      100.0,
      3,
      "APARTAMENTO",
      createAdvertisementRequest(),
      new AddressRequest(1L, "Rua", "123", "Bairro",
        "São Paulo", "SP", "01234-567", null, "Zona"),
      null,
      List.of(1L),
      List.of("https://image.jpg"),
      List.of(1)
    );
  }

  private EstateCreateRequest createEstateRequestWithStandAddress() {
    return new EstateCreateRequest(
      "Casa com Stand",
      "Descrição com stand",
      150.0,
      4,
      "CASA",
      createAdvertisementRequest(),
      createMainAddressRequest(null),
      new AddressRequest(null, "Rua do Stand", "200", "Bairro Stand",
        "São Paulo", "SP", "01234-568", "Sala 10", "Zona Norte"),
      List.of(1L, 2L),
      List.of("http://image1.jpg", "http://image2.jpg"),
      List.of(1, 2)
    );
  }

  private EstateCreateRequest createEstateRequestWithoutImages() {
    return new EstateCreateRequest(
      "Apartamento Sem Fotos",
      "Descrição",
      80.0,
      2,
      "APARTAMENTO",
      createAdvertisementRequest(),
      new AddressRequest(null, "Rua Teste", "123", "Centro",
        "São Paulo", "SP", "01234-567", null, "Zona Sul"),
      null,
      List.of(1L),
      List.of(),
      List.of()
    );
  }

  private EstateCreateRequest createUpdateRequestWithStandAddress() {
    return new EstateCreateRequest(
      "Title",
      "Descrição atualizada",
      120.0,
      3,
      "APARTAMENTO",
      createAdvertisementRequest(),
      createMainAddressRequest(1L),
      new AddressRequest(null, "Rua Stand Nova", "300", "Bairro Novo",
        "São Paulo", "SP", "01234-569", "Loja 5", "Zona Leste"),
      List.of(1L),
      List.of("http://new-image.jpg"),
      List.of(1)
    );
  }

  private EstateCreateRequest createUpdateRequestWithoutStandAddress() {
    return new EstateCreateRequest(
      "Title",
      "Descrição sem stand",
      100.0,
      3,
      "APARTAMENTO",
      createAdvertisementRequest(),
      createMainAddressRequest(1L),
      null,
      List.of(1L),
      List.of("http://image.jpg"),
      List.of(1)
    );
  }

  private EstateCreateRequest createUpdateRequestWithDifferentStandAddress() {
    return new EstateCreateRequest(
      "Title",
      "Descrição",
      100.0,
      3,
      "APARTAMENTO",
      createAdvertisementRequest(),
      createMainAddressRequest(1L),
      new AddressRequest(2L, "New Street", "200", "New Neighborhood",
        "New City", "RJ", "11111-111", "New", "New Region"),
      List.of(1L),
      List.of("http://image.jpg"),
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