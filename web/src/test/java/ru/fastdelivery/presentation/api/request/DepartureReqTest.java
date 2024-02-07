package ru.fastdelivery.presentation.api.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.presentation.api.request.locationRequest.DepartureReq;

public class DepartureReqTest {

  @Test
  void testRecordMethods() {
    BigDecimal testLatitude = new BigDecimal("55.446008");
    BigDecimal testLongitude = new BigDecimal("65.339151");
    DepartureReq departure = new DepartureReq(testLatitude, testLongitude);
    assertThat(departure.latitude()).isEqualTo(testLatitude);
    assertThat(departure.longitude()).isEqualTo(testLongitude);
    DepartureReq sameDeparture = new DepartureReq(testLatitude, testLongitude);
    DepartureReq differentDeparture =
        new DepartureReq(new BigDecimal("11.123456"), new BigDecimal("22.654321"));
    assertThat(departure).isEqualTo(sameDeparture);
    assertThat(departure).isNotEqualTo(differentDeparture);
    assertThat(departure.toString())
        .isEqualTo("DepartureReq[latitude=55.446008, longitude=65.339151]");
  }
}
