package penelope.corretagem.penelopeapirest.infrastructure.api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.ApiResponseWrapper;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.CalUser;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking.BookingCancelRequest;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking.BookingFilterRequest;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking.BookingListResponse;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking.BookingResponse;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.booking.BookingUpdateRequest;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.eventtype.EventTypeCalResponse;
import penelope.corretagem.penelopeapirest.infrastructure.api.dto.eventtype.EventTypeRequest;

import java.util.List;
import java.util.Optional;

@Component
public class CalClient {

  private final RestClient eventTypeRestClient;
  private final RestClient bookingRestClient;

  private static final ParameterizedTypeReference<ApiResponseWrapper<List<EventTypeCalResponse>>> WRAPPER_LIST_EVENT_TYPE =
    new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<ApiResponseWrapper<EventTypeCalResponse>> WRAPPER_EVENT_TYPE =
    new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<ApiResponseWrapper<List<BookingResponse>>> WRAPPER_LIST_BOOKINGS =
    new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<ApiResponseWrapper<BookingResponse>> WRAPPER_BOOKING =
    new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<ApiResponseWrapper<CalUser>> WRAPPER_CAL_USER =
    new ParameterizedTypeReference<>() {};

  public CalClient(
    @Qualifier("calRestClientV1") RestClient eventTypeRestClient,
    @Qualifier("calRestClientV2") RestClient bookingRestClient) {
    this.eventTypeRestClient = eventTypeRestClient;
    this.bookingRestClient = bookingRestClient;
  }

  public CalUser getAuthenticatedUser() {
    return Optional.ofNullable(
      eventTypeRestClient.get()
        .uri("/v2/me")
        .retrieve()
        .body(WRAPPER_CAL_USER))
    .map(ApiResponseWrapper::data)
    .orElse(null);
  }

  public EventTypeCalResponse createEventType(EventTypeRequest request) {
    return Optional.ofNullable(
      eventTypeRestClient.post()
        .uri("/v2/event-types")
        .body(request)
        .retrieve()
        .body(WRAPPER_EVENT_TYPE))
    .map(ApiResponseWrapper::data)
    .orElse(null);
  }

  public List<EventTypeCalResponse> listEventTypes(String username) {

    return Optional.ofNullable(
      eventTypeRestClient.get()
        .uri(uriBuilder -> uriBuilder
          .path("/v2/event-types")
          .queryParam("username", username) // Usa o parâmetro
          .build())
        .retrieve()
        .body(WRAPPER_LIST_EVENT_TYPE))
    .map(ApiResponseWrapper::data)
    .orElse(List.of());
  }

  public EventTypeCalResponse getEventType(Long id) {
    return Optional.ofNullable(
      eventTypeRestClient.get()
        .uri("/v2/event-types/{id}", id)
        .retrieve()
        .body(WRAPPER_EVENT_TYPE))
    .map(ApiResponseWrapper::data)
    .orElse(null);
  }

  public EventTypeCalResponse updateEventType(Long id, EventTypeRequest request) {
    return Optional.ofNullable(
      eventTypeRestClient.patch()
        .uri("/v2/event-types/{id}", id)
        .body(request)
        .retrieve()
        .body(WRAPPER_EVENT_TYPE))
    .map(ApiResponseWrapper::data)
    .orElse(null);
  }

  public void deleteEventType(Long id) {
    eventTypeRestClient.delete()
      .uri("/v2/event-types/{id}", id)
      .retrieve()
      .body(Void.class);
  }

  public BookingListResponse listBookings(BookingFilterRequest request) {

    return Optional.ofNullable(
      bookingRestClient.get()
        .uri(uriBuilder -> {
          var builder = uriBuilder.path("/v2/bookings");

          if (request.eventTypeId() != null) {
            builder.queryParam("eventTypeId", request.eventTypeId());
          }
          if (request.userId() != null) {
            builder.queryParam("userId", request.userId());
          }
          if (request.dateFrom() != null) {
            builder.queryParam("dateFrom", request.dateFrom().toString());
          }
          if (request.dateTo() != null) {
            builder.queryParam("dateTo", request.dateTo().toString());
          }
          if (request.page() != null) {
            builder.queryParam("page", request.page());
          }
          if (request.size() != null) {
            builder.queryParam("size", request.size());
          }

          return builder.build();
        })
        .retrieve()
        .body(WRAPPER_LIST_BOOKINGS))
      .map(wrapper -> new BookingListResponse(wrapper.data(), wrapper.pagination()))
      .orElse(null);
  }

  /**
   * Busca um booking específico por ID
   */
  public BookingResponse getBooking(String uid) {
    return Optional.ofNullable(
      bookingRestClient.get()
        .uri("/v2/bookings/{bookingUid}", uid)
        .retrieve()
        .body(WRAPPER_BOOKING))
      .map(ApiResponseWrapper::data)
      .orElse(null);
  }

  /**
   * Atualiza/Reagenda um booking
   */
  public BookingResponse updateBooking(Long id, BookingUpdateRequest request) {
    return Optional.ofNullable(
      bookingRestClient.patch()
        .uri("/v2/bookings/{id}", id)
        .body(request)
        .retrieve()
        .body(WRAPPER_BOOKING))
      .map(ApiResponseWrapper::data)
      .orElse(null);
  }

  /**
   * Cancela um booking
   */
  public BookingResponse cancelBooking(Long id, BookingCancelRequest request) {
    return Optional.ofNullable(
      bookingRestClient.post()
        .uri("/v2/bookings/{id}/cancel", id)
        .body(request)
        .retrieve()
        .body(WRAPPER_BOOKING))
      .map(ApiResponseWrapper::data)
      .orElse(null);
  }
}
