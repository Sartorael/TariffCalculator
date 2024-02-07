package ru.fastdelivery.presentation.api.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.presentation.api.request.locationRequest.DestinationReq;

public class DestinationReqTest {

  @Test
  void testRecordMethods() {
    // Создание объекта рекорда
    BigDecimal testLatitude = new BigDecimal("55.446008");
    BigDecimal testLongitude = new BigDecimal("65.339151");
    DestinationReq destination = new DestinationReq(testLatitude, testLongitude);

    // Проверка геттеров
    assertThat(destination.latitude()).isEqualTo(testLatitude);
    assertThat(destination.longitude()).isEqualTo(testLongitude);

    // Проверка метода equals()
    DestinationReq sameDestination = new DestinationReq(testLatitude, testLongitude);
    DestinationReq differentDestination =
        new DestinationReq(new BigDecimal("11.123456"), new BigDecimal("22.654321"));
    assertThat(destination).isEqualTo(sameDestination);
    assertThat(destination).isNotEqualTo(differentDestination);

    // Проверка метода toString()
    assertThat(destination.toString())
        .isEqualTo("DestinationReq[latitude=55.446008, longitude=65.339151]");
  }
}
