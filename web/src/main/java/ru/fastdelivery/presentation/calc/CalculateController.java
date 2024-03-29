package ru.fastdelivery.presentation.calc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.location.Departure;
import ru.fastdelivery.domain.common.location.Destination;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.TariffCalculateUseCaseLocation;
import ru.fastdelivery.usecase.TariffCalculateUseCaseVolume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
  private final TariffCalculateUseCase tariffCalculateUseCase;
  private final CurrencyFactory currencyFactory;
  private final TariffCalculateUseCaseVolume tariffCalculateUseCaseVolume;
  private final TariffCalculateUseCaseLocation tariffCalculateUseCaseLocation;
  private static final Logger logger = LoggerFactory.getLogger(CalculateController.class);

  @PostMapping
  @Operation(summary = "Расчет стоимости по упаковкам груза")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
      })
  public ResponseEntity<String> calculate(@Valid @RequestBody CalculatePackagesRequest request)
          throws JsonProcessingException {
    if (request.packages() == null) {
      return handleInvalidRequest();
    }

    Departure departure = createDeparture();
    Destination destination = createDestination();
    List<Pack> packs = createPacks(request, departure, destination);

    Shipment shipment = createShipment(request, packs);
    BigDecimal calculatedPrice = calculateTotalPrice(shipment);

    return createResponseEntity(calculatedPrice, departure, destination);
  }

  private ResponseEntity<String> handleInvalidRequest() {
    logger.error("Invalid request: packages are null");
    return ResponseEntity.badRequest().build();
  }

  private Departure createDeparture() {
    return new Departure(
            tariffCalculateUseCaseLocation.getDepartureLatitude(),
            tariffCalculateUseCaseLocation.getDepartureLongitude()
    );
  }

  private Destination createDestination() {
    return new Destination(
            tariffCalculateUseCaseLocation.getDestinationLatitude(),
            tariffCalculateUseCaseLocation.getDestinationLongitude()
    );
  }

  private List<Pack> createPacks(CalculatePackagesRequest request, Departure departure, Destination destination) {
    return request.packages().stream()
            .map(cargoPackage -> createPack(cargoPackage, departure, destination))
            .collect(Collectors.toList());
  }

  private Pack createPack(CargoPackage cargoPackage, Departure departure, Destination destination) {
    Pack pack = new Pack(
            new Weight(cargoPackage.weight()),
            new Volume(cargoPackage.length(), cargoPackage.width(), cargoPackage.height()),
            departure,
            destination
    );
    return pack;
  }

  private Shipment createShipment(CalculatePackagesRequest request, List<Pack> packs) {
    return new Shipment(packs, currencyFactory.create(request.currencyCode()));
  }
  private BigDecimal calculateTotalPrice(Shipment shipment) {
    var minimalPrice = tariffCalculateUseCase.minimalPrice();
    var totalPrice = calculateBasePrice(shipment).add(calculateVolumePrice(shipment)).add(calculateLocationPrice(shipment));
    return totalPrice.setScale(2, RoundingMode.CEILING);
  }

  private BigDecimal calculateBasePrice(Shipment shipment) {
    return tariffCalculateUseCase.calc(shipment).amount();
  }

  private BigDecimal calculateVolumePrice(Shipment shipment) {
    return tariffCalculateUseCaseVolume.calc(shipment).amount();
  }

  private BigDecimal calculateLocationPrice(Shipment shipment) {
    return tariffCalculateUseCaseLocation.calc(shipment).amount();
  }

  private ResponseEntity<String> createResponseEntity(BigDecimal calculatedPrice, Departure departure, Destination destination) throws JsonProcessingException {
    var minimalPrice = tariffCalculateUseCase.minimalPrice();  // Получение минимальной стоимости из use case
    var calculatedPriceResponse = new Price(calculatedPrice, minimalPrice.currency());
    var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    var responseBody = createResponseBody(calculatedPriceResponse, minimalPrice, departure, destination);
    return ResponseEntity.ok().headers(headers).body(responseBody);
  }

  private String createResponseBody(Price calculatedPriceResponse, Price minimalPrice, Departure departure, Destination destination) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(new CalculatePackagesResponse(calculatedPriceResponse, minimalPrice, departure, destination));
  }

}
