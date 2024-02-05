package ru.fastdelivery.domain.common.location;

import java.math.BigDecimal;

/**
 * Общий класс места отправки
 *
 * @param latitude Широта
 * @param longitude Долгота
 */
public record Departure(BigDecimal latitude, BigDecimal longitude) {
    @Override
    public BigDecimal latitude() {
        return latitude;
    }

    @Override
    public BigDecimal longitude() {
        return longitude;
    }
    public Departure add(Departure departure){
        BigDecimal newLatitude = this.latitude.add(departure.latitude);
        BigDecimal newLongitude = this.longitude.add(departure.longitude);
        return new Departure(newLatitude,newLongitude);
    }
}
