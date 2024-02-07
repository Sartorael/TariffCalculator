package ru.fastdelivery.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.location.Departure;
import ru.fastdelivery.domain.common.location.Destination;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

class TariffCalculateUseCaseTest {

  final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
  final Currency currency = new CurrencyFactory(code -> true).create("RUB");

  final TariffCalculateUseCase tariffCalculateUseCase =
      new TariffCalculateUseCase(weightPriceProvider);

  @Test
  @DisplayName("Расчет стоимости доставки -> успешно")
  void whenCalculatePrice_thenSuccess() {
    // Mock dependencies
    var weightPriceProvider = mock(WeightPriceProvider.class);
    var minimalPrice = new Price(BigDecimal.TEN, currency);
    var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
    when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
    when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);

    // Create objects for test
    var packs =
        List.of(
            new Pack(
                new Weight(BigInteger.valueOf(1200)),
                new Volume(
                    BigDecimal.valueOf(150), BigDecimal.valueOf(150), BigDecimal.valueOf(150)),
                new Departure(BigDecimal.ZERO, BigDecimal.ZERO),
                new Destination(BigDecimal.ZERO, BigDecimal.ZERO)));
    var currency = new CurrencyFactory(code -> true).create("RUB");
    var shipment = new Shipment(packs, currency);
    var expectedPrice = new Price(BigDecimal.valueOf(120), currency);

    // Perform calculation
    var tariffCalculateUseCase = new TariffCalculateUseCase(weightPriceProvider);
    var actualPrice = tariffCalculateUseCase.calc(shipment);

    // Assert the result
    assertThat(actualPrice)
        .usingRecursiveComparison()
        .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
        .isEqualTo(expectedPrice);
  }

  @Test
  @DisplayName("Получение минимальной стоимости -> успешно")
  void whenMinimalPrice_thenSuccess() {
    BigDecimal minimalValue = BigDecimal.TEN;
    var minimalPrice = new Price(minimalValue, currency);
    when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

    var actual = tariffCalculateUseCase.minimalPrice();

    assertThat(actual).isEqualTo(minimalPrice);
  }
}
