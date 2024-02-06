package ru.fastdelivery.properties.provider;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
class PricesRublesPropertiesTest {
    PricesRublesProperties properties;
    CurrencyFactory currencyFactory;
    @Test
    void testDistanceCalc() {
        properties.setDepartureLatitudeStr("45.1234");  // Широта отправления
        properties.setDepartureLongitudeStr("50.5678");  // Долгота отправления
        properties.setDestinationLatitudeStr("55.4321");  // Широта назначения
        properties.setDestinationLongitudeStr("60.8765");  // Долгота назначения
        var expectedDistance = BigDecimal.valueOf(637.0);  // Ожидаемое расстояние в км
        assertEquals(expectedDistance, properties.distanceCalc());
    }


    @Test
    void testGetDepartureLatitude() {
        properties.setDepartureLatitudeStr("45.1234");  // Устанавливаем строковое представление широты
        var expectedLatitude = BigDecimal.valueOf(45.1234);  // Ожидаемое значение широты
        assertEquals(expectedLatitude, properties.getDepartureLatitude());
    }

    @Test
    void testGetDepartureLongitude() {
        properties.setDepartureLongitudeStr("50.5678");
        var expectedLongitude = BigDecimal.valueOf(50.5678);
        assertEquals(expectedLongitude, properties.getDepartureLongitude());
    }

    @Test
    void testGetDestinationLongitude() {
        properties.setDestinationLongitudeStr("60.8765");
        var expectedLongitude = BigDecimal.valueOf(60.8765);
        assertEquals(expectedLongitude, properties.getDestinationLongitude());
    }

    @Test
    void testGetDestinationLatitude() {
        properties.setDestinationLatitudeStr("55.4321");
        var expectedLatitude = BigDecimal.valueOf(55.4321);
        assertEquals(expectedLatitude, properties.getDestinationLatitude());
    }

    @Test
    void testGetMinimalDistance() {
        properties.setMinimalDistance(BigDecimal.valueOf(50));
        var expectedDistance = BigDecimal.valueOf(50);
        assertEquals(expectedDistance, properties.getMinimalDistance());
    }

    @Test
    void testCostPerVolume() {
        properties.setCostPrVolume(BigDecimal.valueOf(10));
        var expectedPrice = new Price(BigDecimal.valueOf(10), currencyFactory.create("RUB"));
        assertEquals(expectedPrice, properties.costPerVolume());
    }

    @Test
    void testSetPerKg() {
        properties.setPerKg(BigDecimal.valueOf(5));
        var expectedPrice = new Price(BigDecimal.valueOf(5), currencyFactory.create("RUB"));
        assertEquals(expectedPrice, properties.costPerKg());
    }

    @Test
    void testSetMinimal() {
        properties.setMinimal(BigDecimal.valueOf(100));
        var expectedPrice = new Price(BigDecimal.valueOf(100), currencyFactory.create("RUB"));
        assertEquals(expectedPrice, properties.minimalPrice());
    }

    @Test
    void testSetCostPrVolume() {
        properties.setCostPrVolume(BigDecimal.valueOf(10));
        var expectedPrice = new Price(BigDecimal.valueOf(10), currencyFactory.create("RUB"));
        assertEquals(expectedPrice, properties.costPerVolume());
    }

    @Test
    void testSetMinimalDistance() {
        properties.setMinimalDistance(BigDecimal.valueOf(50));
        var expectedDistance = BigDecimal.valueOf(50);
        assertEquals(expectedDistance, properties.getMinimalDistance());
    }

    @Test
    void setDepartureLatitudeStr() {
        String departureLatitudeStr = "45.1234";
        properties.setDepartureLatitudeStr(departureLatitudeStr);
        BigDecimal departude = BigDecimal.valueOf(Long.parseLong(departureLatitudeStr));
        assertEquals(departude, properties.getDepartureLatitude());
      }

    @Test
    void testSetDepartureLongitudeStr() {
        String departureLongitude = "50.5678";
        properties.setDepartureLongitudeStr(departureLongitude);
        assertEquals(BigDecimal.valueOf(Long.parseLong(departureLongitude)),
                properties.getDepartureLongitude());
    }

    @Test
    void testSetDestinationLatitudeStr() {
        String destinationLatitude = "55.4321";
        properties.setDestinationLatitudeStr(destinationLatitude);
        assertEquals(BigDecimal.valueOf(Long.parseLong(destinationLatitude)), properties.getDestinationLatitude());
    }

    @Test
    void testSetDestinationLongitudeStr() {
        String destinationLongitude = "60.8765";
        properties.setDestinationLongitudeStr(destinationLongitude);
        assertEquals(BigDecimal.valueOf(Long.parseLong(destinationLongitude)),
                properties.getDestinationLongitude());
    }

    @Test
    void testSetDepartureLatitude() {
        double departureLatitude = 45.1234;
        properties.setDepartureLatitude(departureLatitude);
        assertEquals(BigDecimal.valueOf(departureLatitude), properties.getDepartureLatitude());
    }

    @Test
    void testSetDepartureLongitude() {
        double departureLongitude = 50.5678;
        properties.setDepartureLongitude(departureLongitude);
        assertEquals(BigDecimal.valueOf(departureLongitude), properties.getDepartureLongitude());
    }

    @Test
    void testSetDestinationLatitude() {
        double destinationLatitude = 55.4321;
        properties.setDestinationLatitude(destinationLatitude);
        assertEquals(BigDecimal.valueOf(destinationLatitude), properties.getDestinationLatitude());
    }

    @Test
    void testSetDestinationLongitude() {
        double destinationLongitude = 60.8765;
        properties.setDestinationLongitude(destinationLongitude);
        assertEquals(BigDecimal.valueOf(destinationLongitude), properties.getDestinationLongitude());
    }
}