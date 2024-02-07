package ru.fastdelivery.presentation.api.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.location.Departure;
import ru.fastdelivery.domain.common.location.Destination;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;

class CalculatePackagesResponseTest {

    @Test
    @DisplayName("Если валюты разные -> ошибка создания объекта")
    void whenCurrenciesAreNotEqual_thenException() {
        var calculatedPrice = new Price(new BigDecimal(100), new CurrencyFactory(code -> true).create("USD"));
        var minimalPrice = new Price(new BigDecimal(5), new CurrencyFactory(code -> true).create("RUB"));

        assertThrows(IllegalArgumentException.class,
                () -> new CalculatePackagesResponse(calculatedPrice, minimalPrice,
                        new Departure(BigDecimal.ZERO,BigDecimal.ZERO), new Destination(BigDecimal.ZERO,BigDecimal.ZERO)));
    }

    @Test
    @DisplayName("Если валюты одинаковые -> объект создан")
    void whenCurrenciesAreEqual_thenObjectCreated() {
        var usd = new CurrencyFactory(code -> true).create("USD");
        var calculatedPrice = new Price(new BigDecimal(100), usd);
        var minimalPrice = new Price(new BigDecimal(5), usd);
    var expected =
        new CalculatePackagesResponse(
            new BigDecimal(100), new BigDecimal(5), usd.getCode(), new Destination(BigDecimal
                .ZERO,BigDecimal.ZERO),new Departure(BigDecimal.ZERO,BigDecimal.ZERO));
        var actual = new CalculatePackagesResponse(calculatedPrice, minimalPrice, new Departure(BigDecimal.ZERO,BigDecimal.ZERO), new Destination(BigDecimal.ZERO,BigDecimal.ZERO) );
        assertThat(actual).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expected);
    }

}