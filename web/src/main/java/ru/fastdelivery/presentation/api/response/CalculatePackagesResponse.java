package ru.fastdelivery.presentation.api.response;

import java.math.BigDecimal;
import ru.fastdelivery.domain.common.location.*;
import ru.fastdelivery.domain.common.price.Price;

public record CalculatePackagesResponse(
        BigDecimal totalPrice,
        BigDecimal minimalPrice,
        String currencyCode,
        Destination destination,
        Departure departure
) {
    public CalculatePackagesResponse(Price totalPrice, Price minimalPrice, Departure departure, Destination destination) {
        this(totalPrice.amount(), minimalPrice.amount(), totalPrice.currency().getCode(), destination, departure);

        if (currencyIsNotEqual(totalPrice, minimalPrice)) {
            throw new IllegalArgumentException("Currency codes must be the same");
        }
    }

    private static boolean currencyIsNotEqual(Price priceLeft, Price priceRight) {
        return !priceLeft.currency().equals(priceRight.currency());
    }
}
