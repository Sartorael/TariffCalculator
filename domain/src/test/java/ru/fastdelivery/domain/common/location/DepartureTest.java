package ru.fastdelivery.domain.common.location;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class DepartureTest {

  @Test
  void testRecord() {
    BigDecimal testLatitude = new BigDecimal("55.12345");
    BigDecimal testLongitude = new BigDecimal("37.54321");
    Departure departure = new Departure(testLatitude, testLongitude);
    assertThat(departure.latitude()).isEqualTo(testLatitude);
    assertThat(departure.longitude()).isEqualTo(testLongitude);
    assertThat(departure).isEqualTo(new Departure(testLatitude, testLongitude));
    assertThat(departure.toString()).isEqualTo("Departure[latitude=55.12345, longitude=37.54321]");
  }
}
