package ru.fastdelivery.calc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.locationRequest.DepartureReq;
import ru.fastdelivery.presentation.api.request.locationRequest.DestinationReq;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.TariffCalculateUseCaseLocation;
import ru.fastdelivery.usecase.TariffCalculateUseCaseVolume;

class CalculateControllerTest extends ControllerTest {

  final String baseCalculateApi = "/api/v1/calculate/";
  @MockBean TariffCalculateUseCase useCase;
  @MockBean CurrencyFactory currencyFactory;
  @MockBean TariffCalculateUseCaseVolume tariffCalculateUseCaseVolume;
  @MockBean TariffCalculateUseCaseLocation tariffCalculateUseCaseLocation;

  @Test
  @DisplayName(" Не Валидные данные для расчета стоимость -> Ответ 500")
  void whenInValidInputData_thenReturn500() {
    var departureReq = new DepartureReq(BigDecimal.valueOf(50), BigDecimal.valueOf(50));
    var destinationReq = new DestinationReq(BigDecimal.valueOf(60), BigDecimal.valueOf(60));
    var request =
        new CalculatePackagesRequest(
            List.of(
                new CargoPackage(
                    BigInteger.valueOf(100),
                    BigDecimal.valueOf(1),
                    BigDecimal.valueOf(1),
                    BigDecimal.valueOf(1))),
            "RUB",
            departureReq,
            destinationReq);
    ResponseEntity<CalculatePackagesResponse> response =
        restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  @DisplayName("Список упаковок == null -> Ответ 400")
  void whenEmptyListPackages_thenReturn400() {
    ;
    DepartureReq departureReq = new DepartureReq(BigDecimal.ZERO, BigDecimal.ZERO);
    DestinationReq destinationReq = new DestinationReq(BigDecimal.ZERO, BigDecimal.ZERO);
    var request = new CalculatePackagesRequest(null, "RUB", departureReq, destinationReq);
    ResponseEntity<String> response =
        restTemplate.postForEntity(baseCalculateApi, request, String.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
