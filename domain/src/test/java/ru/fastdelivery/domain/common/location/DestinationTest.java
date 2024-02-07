package ru.fastdelivery.domain.common.location;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class DestinationTest {

  @Test
  void testRecord() {
    BigDecimal testLatitude = new BigDecimal("55.12345");
    BigDecimal testLongitude = new BigDecimal("37.54321");
    Destination destination = new Destination(testLatitude, testLongitude);
    assertThat(destination.latitude()).isEqualTo(testLatitude);
    assertThat(destination.longitude()).isEqualTo(testLongitude);
    assertThat(destination).isEqualTo(new Destination(testLatitude, testLongitude));
    assertThat(destination.toString())
        .isEqualTo("Destination[latitude=55.12345, longitude=37.54321]");
  }
}
