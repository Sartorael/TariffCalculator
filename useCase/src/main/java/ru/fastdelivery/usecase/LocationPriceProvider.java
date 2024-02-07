package ru.fastdelivery.usecase;

import java.math.BigDecimal;
import ru.fastdelivery.domain.common.price.Price;

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
