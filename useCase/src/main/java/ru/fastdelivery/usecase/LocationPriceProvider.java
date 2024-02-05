package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.price.Price;

import java.math.BigDecimal;

public interface LocationPriceProvider {

    Price costPerKm();

    Price minimalPrice();
    BigDecimal distanceCalc();
    BigDecimal getDepartureLatitude();
    BigDecimal getDepartureLongitude();
    BigDecimal getDestinationLongitude();
    BigDecimal getDestinationLatitude();
    BigDecimal getMinimalDistance();
}
