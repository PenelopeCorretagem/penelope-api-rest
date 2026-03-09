//package penelope.corretagem.penelopeapirest.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import penelope.corretagem.penelopeapirest.application.dto.AdvertisementCreateRequest;
//import penelope.corretagem.penelopeapirest.application.dto.EstateCreateRequest;
//import penelope.corretagem.penelopeapirest.data.domain.dto.AddressRequest;
//import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;
//import penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO.AdvertisementCreateRequest;
//import penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO.EstateCreateRequest;
//import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
//import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
//import penelope.corretagem.penelopeapirest.service.AdvertisementComposerService;
//import penelope.corretagem.penelopeapirest.service.AdvertisementService;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("AdvertisementController Unit Tests")
//class AdvertisementControllerTest {
//
//  private MockMvc mockMvc;
//
//  @Mock
//  private AdvertisementService service;
//
//  @Mock
//  private AdvertisementComposerService composerService;
//
//  @InjectMocks
//  private AdvertisementController controller;
//
//  private ObjectMapper objectMapper;
//
//  @BeforeEach
//  void setUp() {
//    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    objectMapper = new ObjectMapper();
//    objectMapper.findAndRegisterModules();
//  }
//
//  @Test
//  @DisplayName("Deve retornar todos os anúncios quando listAll é chamado")
//  void shouldReturnAllAdvertisements() throws Exception {
//    // Arrange
//    List<AdvertisementResponse> mockAdvertisements = Arrays.asList(
//      createMockAdvertisementResponse(1L),
//      createMockAdvertisementResponse(2L)
//    );
//
//    when(service.getAllAdvertisements(ArgumentMatchers.any()))
//      .thenReturn(mockAdvertisements);
//
//    // Act
//    mockMvc.perform(get("/advertisement")
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$", hasSize(2)))
//      .andExpect(jsonPath("$[0].id", is(1)))
//      .andExpect(jsonPath("$[1].id", is(2)));
//
//    // Assert
//    verify(service, times(1)).getAllAdvertisements(ArgumentMatchers.any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncios filtrados quando parâmetros de filtro são fornecidos")
//  void shouldReturnFilteredAdvertisements() throws Exception {
//    // Arrange
//    List<AdvertisementResponse> mockAdvertisements = List.of(
//      createMockAdvertisementResponse(1L)
//    );
//
//    when(service.getAllAdvertisements(ArgumentMatchers.any()))
//      .thenReturn(mockAdvertisements);
//
//    // Act
//    mockMvc.perform(get("/advertisement")
//        .param("city", "São Paulo")
//        .param("region", "Centro")
//        .param("type", "APARTMENT")
//        .param("numberOfRooms", "3")
//        .param("active", "true")
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$", hasSize(1)))
//      .andExpect(jsonPath("$[0].id", is(1)));
//
//    // Assert
//    verify(service, times(1)).getAllAdvertisements(ArgumentMatchers.any());
//  }
//
//  @Test
//  @DisplayName("Deve retornar o anúncio mais recente quando getLatestAdvertisement é chamado")
//  void shouldReturnLatestAdvertisement() throws Exception {
//    // Arrange
//    AdvertisementResponse mockResponse = createMockAdvertisementResponse(1L);
//
//    when(service.getLatestAdvertisement()).thenReturn(mockResponse);
//
//    // Act
//    mockMvc.perform(get("/advertisement/latest")
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$.id", is(1)))
//      .andExpect(jsonPath("$.active", is(true)));
//
//    // Assert
//    verify(service, times(1)).getLatestAdvertisement();
//  }
//
//  @Test
//  @DisplayName("Deve retornar 404 quando o anúncio mais recente não é encontrado")
//  void shouldReturn404WhenLatestAdvertisementNotFound() throws Exception {
//    // Arrange
//    when(service.getLatestAdvertisement()).thenReturn(null);
//
//    // Act
//    mockMvc.perform(get("/advertisement/latest")
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//
//    // Assert
//    verify(service, times(1)).getLatestAdvertisement();
//  }
//
//  @Test
//  @DisplayName("Deve retornar anúncio por id quando getAdvertisementById é chamado")
//  void shouldReturnAdvertisementById() throws Exception {
//    // Arrange
//    Long advertisementId = 1L;
//    AdvertisementResponse mockResponse = createMockAdvertisementResponse(advertisementId);
//
//    when(service.getAdvertisementById(advertisementId)).thenReturn(mockResponse);
//
//    // Act
//    mockMvc.perform(get("/advertisement/{id}", advertisementId)
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$.id", is(1)))
//      .andExpect(jsonPath("$.active", is(true)));
//
//    // Assert
//    verify(service, times(1)).getAdvertisementById(advertisementId);
//  }
//
//  @Test
//  @DisplayName("Deve retornar 404 quando anúncio por id não é encontrado")
//  void shouldReturn404WhenAdvertisementByIdNotFound() throws Exception {
//    // Arrange
//    Long advertisementId = 999L;
//    when(service.getAdvertisementById(advertisementId)).thenReturn(null);
//
//    // Act
//    mockMvc.perform(get("/advertisement/{id}", advertisementId)
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//
//    // Assert
//    verify(service, times(1)).getAdvertisementById(advertisementId);
//  }
//
//  @Test
//  @DisplayName("Deve criar anúncio e retornar status 201")
//  void shouldCreateAdvertisement() throws Exception {
//    // Arrange
//    EstateCreateRequest request = createMockEstateCreateRequest();
//    AdvertisementEntity mockEntity = new AdvertisementEntity();
//    mockEntity.setId(1L);
//
//    when(composerService.createAdvertisement(ArgumentMatchers.any()))
//      .thenReturn(Optional.of(mockEntity));
//
//    // Act
//    mockMvc.perform(post("/advertisement")
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(objectMapper.writeValueAsString(request)))
//      .andExpect(status().isCreated());
//
//    // Assert
//    verify(composerService, times(1)).createAdvertisement(ArgumentMatchers.any());
//  }
//
//  @Test
//  @DisplayName("Deve fazer upload de imagens e retornar URLs")
//  void shouldUploadImages() throws Exception {
//    // Arrange
//    MockMultipartFile file1 = new MockMultipartFile(
//      "files",
//      "image1.jpg",
//      MediaType.IMAGE_JPEG_VALUE,
//      "image content 1".getBytes()
//    );
//    MockMultipartFile file2 = new MockMultipartFile(
//      "files",
//      "image2.jpg",
//      MediaType.IMAGE_JPEG_VALUE,
//      "image content 2".getBytes()
//    );
//
//    List<String> mockUrls = Arrays.asList(
//      "https://cloudinary.com/image1.jpg",
//      "https://cloudinary.com/image2.jpg"
//    );
//
//    when(composerService.uploadImages(anyList())).thenReturn(mockUrls);
//
//    // Act
//    mockMvc.perform(multipart("/advertisement/photos")
//        .file(file1)
//        .file(file2)
//        .contentType(MediaType.MULTIPART_FORM_DATA))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$", hasSize(2)))
//      .andExpect(jsonPath("$[0]", is("https://cloudinary.com/image1.jpg")))
//      .andExpect(jsonPath("$[1]", is("https://cloudinary.com/image2.jpg")));
//
//    // Assert
//    verify(composerService, times(1)).uploadImages(anyList());
//  }
//
//  @Test
//  @DisplayName("Deve atualizar anúncio e retornar status 204")
//  void shouldUpdateAdvertisement() throws Exception {
//    // Arrange
//    Long estateId = 1L;
//    EstateCreateRequest request = createMockEstateCreateRequest();
//    AdvertisementEntity mockEntity = new AdvertisementEntity();
//    mockEntity.setId(estateId);
//
//    when(composerService.updateAdvertisement(eq(estateId), ArgumentMatchers.any()))
//      .thenReturn(mockEntity);
//
//    // Act
//    mockMvc.perform(put("/advertisement/{id}", estateId)
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(objectMapper.writeValueAsString(request)))
//      .andExpect(status().isNoContent());
//
//    // Assert
//    verify(composerService, times(1)).updateAdvertisement(eq(estateId), ArgumentMatchers.any());
//  }
//
//  @Test
//  @DisplayName("Deve desativar anúncio e retornar status 204")
//  void shouldDeactivateAdvertisement() throws Exception {
//    // Arrange
//    Long advertisementId = 1L;
//    Boolean active = false;
//
//    doNothing().when(composerService).updateAdvertisementStatus(advertisementId, active);
//
//    // Act
//    mockMvc.perform(patch("/advertisement/{id}", advertisementId)
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(objectMapper.writeValueAsString(active)))
//      .andExpect(status().isNoContent());
//
//    // Assert
//    verify(composerService, times(1)).updateAdvertisementStatus(advertisementId, active);
//  }
//
//  @Test
//  @DisplayName("Deve ativar anúncio e retornar status 204")
//  void shouldActivateAdvertisement() throws Exception {
//    // Arrange
//    Long advertisementId = 1L;
//    Boolean active = true;
//
//    doNothing().when(composerService).updateAdvertisementStatus(advertisementId, active);
//
//    // Act
//    mockMvc.perform(patch("/advertisement/{id}", advertisementId)
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(objectMapper.writeValueAsString(active)))
//      .andExpect(status().isNoContent());
//
//    // Assert
//    verify(composerService, times(1)).updateAdvertisementStatus(advertisementId, active);
//  }
//
//  @Test
//  @DisplayName("Deve retornar lista vazia quando não existem anúncios")
//  void shouldReturnEmptyListWhenNoAdvertisements() throws Exception {
//    // Arrange
//    when(service.getAllAdvertisements(ArgumentMatchers.any()))
//      .thenReturn(new ArrayList<>());
//
//    // Act
//    mockMvc.perform(get("/advertisement")
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$", hasSize(0)));
//
//    // Assert
//    verify(service, times(1)).getAllAdvertisements(ArgumentMatchers.any());
//  }
//
//  // Helper methods
//
//  private AdvertisementResponse createMockAdvertisementResponse(Long id) {
//    return new AdvertisementResponse(
//      id,
//      true,
//      false,
//      LocalDateTime.now(),
//      LocalDate.now().plusDays(30),
//      null,
//      null,
//      null,
//      null
//    );
//  }
//
//  private EstateCreateRequest createMockEstateCreateRequest() {
//    AddressRequest addressRequest = new AddressRequest(
//      null,
//      "Rua Teste",
//      "123",
//      "Centro",
//      "São Paulo",
//      "SP",
//      "01234-567",
//      null,
//      "Zona Sul"
//    );
//
//    AdvertisementCreateRequest advertisementRequest = new AdvertisementCreateRequest(
//      1L,
//      2L,
//      LocalDate(),
//      true
//    );
//
//    return new EstateCreateRequest(
//      "Apartamento Novo",
//      "Descrição do apartamento",
//      100.0,
//      3,
//      "APARTAMENTO",
//      advertisementRequest,
//      addressRequest,
//      null,
//      List.of(1L, 2L),
//      List.of("https://example.com/image1.jpg"),
//      List.of(1)
//    );
//  }
//}
