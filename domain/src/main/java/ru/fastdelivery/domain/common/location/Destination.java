package ru.fastdelivery.domain.common.location;

import java.math.BigDecimal;

/**
 * Общий класс места приёма
 *
 * @param latitude Широта
 * @param longitude Долгота
 */
public record Destination(BigDecimal latitude, BigDecimal longitude) {
    @Override
    public BigDecimal latitude() {
        return latitude;
    }

    @Override
    public BigDecimal longitude() {
        return longitude;
    }
    public Destination add(Destination destination) {
        BigDecimal newLatitude = this.latitude.add(destination.latitude);
        BigDecimal newLongitude = this.longitude.add(destination.longitude);
        return new Destination(newLatitude, newLongitude);
    }
}
