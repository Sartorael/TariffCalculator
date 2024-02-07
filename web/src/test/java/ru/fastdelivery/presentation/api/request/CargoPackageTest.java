package ru.fastdelivery.presentation.api.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

public class CargoPackageTest {

  @Test
  void testRecordMethods() {
    BigInteger testWeight = BigInteger.valueOf(566745);
    BigDecimal testLength = BigDecimal.valueOf(560);
    BigDecimal testWidth = BigDecimal.valueOf(100);
    BigDecimal testHeight = BigDecimal.valueOf(200);
    CargoPackage cargoPackage = new CargoPackage(testWeight, testLength, testWidth, testHeight);
    assertThat(cargoPackage.weight()).isEqualTo(testWeight);
    assertThat(cargoPackage.length()).isEqualTo(testLength);
    assertThat(cargoPackage.width()).isEqualTo(testWidth);
    assertThat(cargoPackage.height()).isEqualTo(testHeight);
    CargoPackage sameCargoPackage = new CargoPackage(testWeight, testLength, testWidth, testHeight);
    CargoPackage differentCargoPackage =
        new CargoPackage(
            BigInteger.valueOf(12345),
            BigDecimal.valueOf(100),
            BigDecimal.valueOf(50),
            BigDecimal.valueOf(75));
    assertThat(cargoPackage).isEqualTo(sameCargoPackage);
    assertThat(cargoPackage).isNotEqualTo(differentCargoPackage);
    assertThat(cargoPackage.toString())
        .isEqualTo("CargoPackage[weight=566745, length=560, width=100, height=200]");
  }
}
