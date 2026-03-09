//package penelope.corretagem.penelopeapirest.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.jpa.domain.Specification;
//import penelope.corretagem.penelopeapirest.core.user.User;
//import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AdvertisementFilterRequest;
//import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;
//import penelope.corretagem.penelopeapirest.core.address.Address;
//import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
//import penelope.corretagem.penelopeapirest.core.estate.Estate;
//import penelope.corretagem.penelopeapirest.data.domain.entity.EventTypeEntity;
//import penelope.corretagem.penelopeapirest.data.domain.repository.AdvertisementRepository;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AdvertisementServiceTest {
//
//  @Mock
//  private AdvertisementRepository repository;
//
//  @InjectMocks
//  private AdvertisementService service;
//
//  private Advertisement advertisementEntity;
//
//  @BeforeEach
//  void setUp() {
//    Estate estate = getEstateEntity();
//
//    User user = new User();
//    user.setId(1L);
//
//    EventTypeEntity eventType = new EventTypeEntity();
//    eventType.setId(1L);
//    eventType.setTitle("Teste Evento");
//    eventType.setSlug("teste-evento");
//
//    advertisementEntity = new Advertisement();
//    advertisementEntity.setId(1L);
//    advertisementEntity.setActive(true);
//    advertisementEntity.setEmphasis(false);
//    advertisementEntity.setCreatedAt(LocalDateTime.now());
//    advertisementEntity.setEndDate(LocalDate.now().plusDays(30));
//    advertisementEntity.setProperty(estate);
//    advertisementEntity.setCreator(user);
//    advertisementEntity.setResponsible(user);
//    advertisementEntity.setEventType(eventType);
//  }
//
//  // ========== Testes básicos de getAllAdvertisements ==========
//
//  @Test
//  @DisplayName("Deve retornar lista de anúncios")
//  void getAllAdvertisements_shouldReturnListOfAdvertisements() {
//    // Arrange
//    AdvertisementFilterRequest request = createEmptyFilterRequest();
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar lista filtrada quando filtros são fornecidos")
//  void getAllAdvertisements_withFilters_shouldReturnFilteredList() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      "Sao Paulo", null, "DISPONIVEL", null, true, null, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar lista vazia")
//  void getAllAdvertisements_shouldReturnEmptyList() {
//    // Arrange
//    AdvertisementFilterRequest request = createEmptyFilterRequest();
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(List.of());
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertTrue(result.isEmpty());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar todos os anúncios quando há múltiplos resultados")
//  void getAllAdvertisements_withMultipleResults_shouldReturnAll() {
//    // Arrange
//    Advertisement ad2 = createSecondAdvertisement();
//    AdvertisementFilterRequest request = createEmptyFilterRequest();
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Arrays.asList(advertisementEntity, ad2));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(2, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  // ========== Testes de filtros individuais ==========
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por cidade")
//  void getAllAdvertisements_withCityFilter_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      "Sao Paulo", null, null, null, null, null, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por região")
//  void getAllAdvertisements_withRegionFilter_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, "Sudeste", null, null, null, null, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por tipo")
//  void getAllAdvertisements_withTypeFilter_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, "DISPONIVEL", null, null, null, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por número de quartos")
//  void getAllAdvertisements_withNumberOfRoomsFilter_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, 3, null, null, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por status ativo")
//  void getAllAdvertisements_withActiveFilter_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, true, null, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar lista vazia com filtro de inativos")
//  void getAllAdvertisements_withInactiveFilter_shouldReturnEmpty() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, false, null, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(List.of());
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertTrue(result.isEmpty());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por área")
//  void getAllAdvertisements_withAreaFilter_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, 100.0, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por título")
//  void getAllAdvertisements_withTitleFilter_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, null, "Teste", null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por descrição")
//  void getAllAdvertisements_withDescriptionFilter_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, null, null, "Descrição", null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  // ========== Testes de filtros de data ==========
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por data de criação")
//  void getAllAdvertisements_withCreatedAtFilter_shouldReturnFiltered() {
//    // Arrange
//    LocalDate date = LocalDate.now();
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, null, null, null, date, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por data de criação mínima")
//  void getAllAdvertisements_withCreatedAtMinFilter_shouldReturnFiltered() {
//    // Arrange
//    LocalDate date = LocalDate.now().minusDays(1);
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, null, null, null, null, date, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por data de criação máxima")
//  void getAllAdvertisements_withCreatedAtMaxFilter_shouldReturnFiltered() {
//    // Arrange
//    LocalDate date = LocalDate.now().plusDays(1);
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, null, null, null, null, null, date, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por data de término")
//  void getAllAdvertisements_withEndDateFilter_shouldReturnFiltered() {
//    // Arrange
//    LocalDate date = LocalDate.now().plusDays(30);
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, null, null, null, null, null, null, date, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por data de término mínima")
//  void getAllAdvertisements_withEndDateMinFilter_shouldReturnFiltered() {
//    // Arrange
//    LocalDate date = LocalDate.now();
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, null, null, null, null, null, null, null, date, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados por data de término máxima")
//  void getAllAdvertisements_withEndDateMaxFilter_shouldReturnFiltered() {
//    // Arrange
//    LocalDate date = LocalDate.now().plusDays(60);
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      null, null, null, null, null, null, null, null, null, null, null, null, null, date
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados com múltiplos filtros")
//  void getAllAdvertisements_withMultipleFilters_shouldReturnFiltered() {
//    // Arrange
//    AdvertisementFilterRequest request = new AdvertisementFilterRequest(
//      "Sao Paulo", "Sudeste", "DISPONIVEL", 3, true, 100.0, null, null, null, null, null, null, null, null
//    );
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    List<AdvertisementResponse> result = service.getAllAdvertisements(request);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1, result.size());
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  // ========== Testes de getLatestAdvertisement ==========
//
//  @Test
//  @DisplayName("Deve retornar o anúncio mais recente")
//  void getLatestAdvertisement_shouldReturnLatestAdvertisement() {
//    // Arrange
//    when(repository.findTopByOrderByCreatedAtDesc()).thenReturn(Optional.of(advertisementEntity));
//
//    // Act
//    AdvertisementResponse result = service.getLatestAdvertisement();
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1L, result.id());
//    verify(repository, times(1)).findTopByOrderByCreatedAtDesc();
//  }
//
//  @Test
//  @DisplayName("Deve retornar null quando não há anúncios")
//  void getLatestAdvertisement_shouldReturnNull() {
//    // Arrange
//    when(repository.findTopByOrderByCreatedAtDesc()).thenReturn(Optional.empty());
//
//    // Act
//    AdvertisementResponse result = service.getLatestAdvertisement();
//
//    // Assert
//    assertNull(result);
//    verify(repository, times(1)).findTopByOrderByCreatedAtDesc();
//  }
//
//  @Test
//  @DisplayName("Deve retornar dados corretos do anúncio mais recente")
//  void getLatestAdvertisement_shouldReturnCorrectData() {
//    // Arrange
//    when(repository.findTopByOrderByCreatedAtDesc()).thenReturn(Optional.of(advertisementEntity));
//
//    // Act
//    AdvertisementResponse result = service.getLatestAdvertisement();
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1L, result.id());
//    assertTrue(result.active());
//    verify(repository).findTopByOrderByCreatedAtDesc();
//  }
//
//  // ========== Testes de getAdvertisementById ==========
//
//  @Test
//  @DisplayName("Deve retornar anúncio por id")
//  void getAdvertisementById_shouldReturnAdvertisement() {
//    // Arrange
//    when(repository.findByIdWithAllRelations(1L)).thenReturn(Optional.of(advertisementEntity));
//
//    // Act
//    AdvertisementResponse result = service.getAdvertisementById(1L);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1L, result.id());
//    verify(repository, times(1)).findByIdWithAllRelations(1L);
//  }
//
//  @Test
//  @DisplayName("Deve retornar null quando anúncio não é encontrado")
//  void getAdvertisementById_shouldReturnNull() {
//    // Arrange
//    when(repository.findByIdWithAllRelations(1L)).thenReturn(Optional.empty());
//
//    // Act
//    AdvertisementResponse result = service.getAdvertisementById(1L);
//
//    // Assert
//    assertNull(result);
//    verify(repository, times(1)).findByIdWithAllRelations(1L);
//  }
//
//  @Test
//  @DisplayName("Deve retornar dados corretos do anúncio por id válido")
//  void getAdvertisementById_withValidId_shouldReturnCorrectData() {
//    // Arrange
//    when(repository.findByIdWithAllRelations(1L)).thenReturn(Optional.of(advertisementEntity));
//
//    // Act
//    AdvertisementResponse result = service.getAdvertisementById(1L);
//
//    // Assert
//    assertNotNull(result);
//    assertEquals(1L, result.id());
//    assertTrue(result.active());
//    assertFalse(result.emphasis());
//    verify(repository).findByIdWithAllRelations(1L);
//  }
//
//  @Test
//  @DisplayName("Deve retornar null com id diferente")
//  void getAdvertisementById_withDifferentId_shouldReturnNull() {
//    // Arrange
//    when(repository.findByIdWithAllRelations(999L)).thenReturn(Optional.empty());
//
//    // Act
//    AdvertisementResponse result = service.getAdvertisementById(999L);
//
//    // Assert
//    assertNull(result);
//    verify(repository).findByIdWithAllRelations(999L);
//  }
//
//  // ========== Testes de verificação de interações ==========
//
//  @Test
//  @DisplayName("Deve chamar repositório com todos os filtros nulos")
//  void getAllAdvertisements_withAllFiltersNull_shouldCallRepository() {
//    // Arrange
//    AdvertisementFilterRequest request = createEmptyFilterRequest();
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(Collections.singletonList(advertisementEntity));
//
//    // Act
//    service.getAllAdvertisements(request);
//
//    // Assert
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//  }
//
//  @Test
//  @DisplayName("Deve verificar interação com repositório ao buscar por id")
//  void getAdvertisementById_shouldVerifyRepositoryInteraction() {
//    // Arrange
//    when(repository.findByIdWithAllRelations(1L)).thenReturn(Optional.of(advertisementEntity));
//
//    // Act
//    service.getAdvertisementById(1L);
//
//    // Assert
//    verify(repository, times(1)).findByIdWithAllRelations(1L);
//    verifyNoMoreInteractions(repository);
//  }
//
//  @Test
//  @DisplayName("Deve verificar interação com repositório ao buscar mais recente")
//  void getLatestAdvertisement_shouldVerifyRepositoryInteraction() {
//    // Arrange
//    when(repository.findTopByOrderByCreatedAtDesc()).thenReturn(Optional.of(advertisementEntity));
//
//    // Act
//    service.getLatestAdvertisement();
//
//    // Assert
//    verify(repository, times(1)).findTopByOrderByCreatedAtDesc();
//    verifyNoMoreInteractions(repository);
//  }
//
//  @Test
//  @DisplayName("Deve verificar interação com repositório ao buscar todos")
//  void getAllAdvertisements_shouldVerifyRepositoryInteraction() {
//    // Arrange
//    AdvertisementFilterRequest request = createEmptyFilterRequest();
//    when(repository.findAll(ArgumentMatchers.<Specification<Advertisement>>any()))
//      .thenReturn(List.of());
//
//    // Act
//    service.getAllAdvertisements(request);
//
//    // Assert
//    verify(repository, times(1)).findAll(ArgumentMatchers.<Specification<Advertisement>>any());
//    verifyNoMoreInteractions(repository);
//  }
//
//  // ========== Métodos auxiliares ==========
//
//  private AdvertisementFilterRequest createEmptyFilterRequest() {
//    return new AdvertisementFilterRequest(
//      null, null, null, null, null, null, null, null, null, null, null, null, null, null
//    );
//  }
//
//  private Advertisement createSecondAdvertisement() {
//    Advertisement ad2 = new Advertisement();
//    ad2.setId(2L);
//    ad2.setActive(true);
//    ad2.setEmphasis(true);
//    ad2.setCreatedAt(LocalDateTime.now());
//    ad2.setEndDate(LocalDate.now().plusDays(15));
//    ad2.setProperty(advertisementEntity.getProperty());
//    ad2.setCreator(advertisementEntity.getCreator());
//    ad2.setResponsible(advertisementEntity.getResponsible());
//    ad2.setEventType(advertisementEntity.getEventType());
//    return ad2;
//  }
//
//  private static Estate getEstateEntity() {
//    Address address = new Address();
//    address.setId(1L);
//    address.setStreet("Rua Teste");
//    address.setNumber("123");
//    address.setNeighborhood("Centro");
//    address.setCity("Sao Paulo");
//    address.setUf("SP");
//    address.setZipCode("12345678");
//    address.setRegion("Sudeste");
//
//    Estate estate = new Estate();
//    estate.setId(1L);
//    estate.setTitle("Teste");
//    estate.setDescription("Descrição teste");
//    estate.setArea(100.0);
//    estate.setNumberOfRooms(3);
//    estate.setType(Estate.Type.DISPONIVEL);
//    estate.setAddress(address);
//    estate.setImages(new java.util.HashSet<>());
//    estate.setAmenities(new java.util.HashSet<>());
//    return estate;
//  }
//}
