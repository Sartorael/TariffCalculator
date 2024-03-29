package ru.fastdelivery.domain.delivery.pack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.location.Departure;
import ru.fastdelivery.domain.common.location.Destination;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

class PackTest {

  @Test
  void whenWeightMoreThanMaxWeight_thenThrowException() {
    var weight = new Weight(BigInteger.valueOf(150_001));
    assertThatThrownBy(
            () ->
                new Pack(
                    weight,
                    new Volume(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
                    new Departure(BigDecimal.ZERO, BigDecimal.ZERO),
                    new Destination(BigDecimal.ZERO, BigDecimal.ZERO)))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenWeightLessThanMaxWeight_thenObjectCreated() {
    var actual =
        new Pack(
            new Weight(BigInteger.valueOf(1_000)),
            new Volume(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
            new Departure(BigDecimal.ZERO, BigDecimal.ZERO),
            new Destination(BigDecimal.ZERO, BigDecimal.ZERO));
    assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
  }

  @Test
  void whenVolumeMoreThanMaxVolume_thenThrowException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          var volume =
              new Volume(
                  BigDecimal.valueOf(1501), BigDecimal.valueOf(150), BigDecimal.valueOf(150));
          var weight = new Weight(BigInteger.valueOf(1_000));
          var departure = new Departure(BigDecimal.ZERO, BigDecimal.ZERO);
          var destination = new Destination(BigDecimal.ZERO, BigDecimal.ZERO);
          new Pack(weight, volume, departure, destination);
        });
  }

  @Test
  void whenWidthMoreThanMaxVolume_thenThrowException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          var volume =
              new Volume(
                  BigDecimal.valueOf(1600), BigDecimal.valueOf(150), BigDecimal.valueOf(150));
          var weight = new Weight(BigInteger.valueOf(1_000));
          var departure = new Departure(BigDecimal.ZERO, BigDecimal.ZERO);
          var destination = new Destination(BigDecimal.ZERO, BigDecimal.ZERO);
          new Pack(weight, volume, departure, destination);
        });
  }

  @Test
  void whenLengthMoreThanMaxVolume_thenThrowException() {
    assertThatThrownBy(
            () -> {
              var volume =
                  new Volume(
                      BigDecimal.valueOf(0), BigDecimal.valueOf(1501), BigDecimal.valueOf(150));
              var weight = new Weight(BigInteger.valueOf(1_000));
              var departure = new Departure(BigDecimal.ZERO, BigDecimal.ZERO);
              var destination = new Destination(BigDecimal.ZERO, BigDecimal.ZERO);
              new Pack(weight, volume, departure, destination);
            })
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenHeightMoreThanMaxVolume_thenThrowException() {
    assertThatThrownBy(
            () -> {
              var volume =
                  new Volume(
                      BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(1501));
              var weight = new Weight(BigInteger.valueOf(1_000));
              var departure = new Departure(BigDecimal.ZERO, BigDecimal.ZERO);
              var destination = new Destination(BigDecimal.ZERO, BigDecimal.ZERO);
              new Pack(weight, volume, departure, destination);
            })
        .isInstanceOf(IllegalArgumentException.class);
  }
}
