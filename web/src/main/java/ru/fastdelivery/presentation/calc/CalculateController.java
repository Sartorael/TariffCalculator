package ru.fastdelivery.presentation.calc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
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

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
  private final TariffCalculateUseCase tariffCalculateUseCase;
  private final CurrencyFactory currencyFactory;
  private final TariffCalculateUseCaseVolume tariffCalculateUseCaseVolume;
  private final TariffCalculateUseCaseLocation tariffCalculateUseCaseLocation;

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
      return ResponseEntity.badRequest().build();
    }
    List<Pack> packs = new ArrayList<>();
    Departure departure =
        new Departure(
            tariffCalculateUseCaseLocation.getDepartureLatitude(),
            tariffCalculateUseCaseLocation.getDepartureLongitude());
    Destination destination =
        new Destination(
            tariffCalculateUseCaseLocation.getDestinationLatitude(),
            tariffCalculateUseCaseLocation.getDestinationLongitude());
    for (CargoPackage cargoPackage : request.packages()) {
      Weight weight = new Weight(cargoPackage.weight());
      Volume volume =
          new Volume(cargoPackage.length(), cargoPackage.width(), cargoPackage.height());
      Pack pack = new Pack(weight, volume, departure, destination);
      packs.add(pack);
    }
    var shipment = new Shipment(packs, currencyFactory.create(request.currencyCode()));
    var minimalPrice = tariffCalculateUseCase.minimalPrice();
    var totalPrice =
        tariffCalculateUseCase
            .calc(shipment)
            .amount()
            .add(tariffCalculateUseCaseVolume.calc(shipment).amount())
            .add(tariffCalculateUseCaseLocation.calc(shipment).amount());
    totalPrice = totalPrice.setScale(2, RoundingMode.CEILING);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    var calculatedPriceResponse = new Price(totalPrice, minimalPrice.currency());
    String responseBody =
        objectMapper.writeValueAsString(
            new CalculatePackagesResponse(
                calculatedPriceResponse, minimalPrice, departure, destination));

    return ResponseEntity.ok().headers(headers).body(responseBody);
  }
}
