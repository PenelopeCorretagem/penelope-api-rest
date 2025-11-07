package penelope.corretagem.penelopeapirest.data.domain.dto;

import java.math.BigDecimal;
import java.util.Set;

public record EstateRequest(
  String name,
  String title,
  String description,
  BigDecimal price,
  Double area,
  Integer numberOfRooms,
  Integer numberOfBathrooms,
  Integer numberOfVacancies,
  String type,
  AddressRequest address,
  Set<ImageEstateRequest> images
) {}