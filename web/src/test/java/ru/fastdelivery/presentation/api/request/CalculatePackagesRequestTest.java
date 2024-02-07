package ru.fastdelivery.presentation.api.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.presentation.api.request.locationRequest.DepartureReq;
import ru.fastdelivery.presentation.api.request.locationRequest.DestinationReq;

public class CalculatePackagesRequestTest {

  @Test
  void testRecordMethods() {
    List<CargoPackage> testPackages = new ArrayList<>();
    testPackages.add(
        new CargoPackage(
            BigInteger.valueOf(405645),
            BigDecimal.valueOf(100),
            BigDecimal.valueOf(50),
            BigDecimal.valueOf(75)));
    String testCurrencyCode = "RUB";
    DepartureReq testDepartureReq =
        new DepartureReq(BigDecimal.valueOf(55.446008), BigDecimal.valueOf(65.339151));
    DestinationReq testDestinationReq =
        new DestinationReq(BigDecimal.valueOf(60.446008), BigDecimal.valueOf(70.339151));
    CalculatePackagesRequest calculateRequest =
        new CalculatePackagesRequest(
            testPackages, testCurrencyCode, testDepartureReq, testDestinationReq);
    assertThat(calculateRequest.packages()).isEqualTo(testPackages);
    assertThat(calculateRequest.currencyCode()).isEqualTo(testCurrencyCode);
    assertThat(calculateRequest.departureReq()).isEqualTo(testDepartureReq);
    assertThat(calculateRequest.destinationReq()).isEqualTo(testDestinationReq);
    CalculatePackagesRequest sameCalculateRequest =
        new CalculatePackagesRequest(
            testPackages, testCurrencyCode, testDepartureReq, testDestinationReq);
    assertThat(calculateRequest).isEqualTo(sameCalculateRequest);
    assertThat(calculateRequest.toString())
        .isEqualTo(
            "CalculatePackagesRequest[packages=[CargoPackage"
                + "[weight=405645, length=100, width=50, height=75]], currencyCode=RUB, departureReq=DepartureReq"
                + "[latitude=55.446008, longitude=65.339151], destinationReq=DestinationReq[latitude=60.446008,"
                + " longitude=70.339151]]");
  }
}
