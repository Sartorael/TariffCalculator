package ru.fastdelivery.properties.provider;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.LocationPriceProvider;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

import java.math.BigDecimal;

/**
 * Настройки базовых цен стоимости перевозки из конфига
 */
@ConfigurationProperties("cost.rub")
@Setter
public class PricesRublesProperties implements WeightPriceProvider, VolumePriceProvider , LocationPriceProvider {

    private BigDecimal perKg;
    private BigDecimal minimal;
    private BigDecimal costPrVolume;
    private BigDecimal minimalDistance;

    @Autowired
    private CurrencyFactory currencyFactory;

    @Override
    public Price costPerKg() {
        return new Price(perKg, currencyFactory.create("RUB"));
    }

    @Override
    public Price costPerKm() {
        return new Price(BigDecimal.valueOf(1), currencyFactory.create("RUB"));
    }

    @Override
    public Price minimalPrice() {
        return new Price(minimal, currencyFactory.create("RUB"));
    }

    @Value("${location.departure.latitude}")
    private String departureLatitudeStr;

    @Value("${location.departure.longitude}")
    private String departureLongitudeStr;

    @Value("${location.destination.latitude}")
    private String destinationLatitudeStr;

    @Value("${location.destination.longitude}")
    private String destinationLongitudeStr;

    private double departureLatitude;
    private double departureLongitude;
    private double destinationLatitude;
    private double destinationLongitude;

    @PostConstruct
    public void init() {
        departureLatitude = Double.parseDouble(departureLatitudeStr);
        departureLongitude = Double.parseDouble(departureLongitudeStr);
        destinationLatitude = Double.parseDouble(destinationLatitudeStr);
        destinationLongitude = Double.parseDouble(destinationLongitudeStr);
    }

    @Override
    public BigDecimal distanceCalc() {
    Coordinate lat = Coordinate.fromDegrees(departureLatitude);
    Coordinate lng = Coordinate.fromDegrees(departureLongitude);
        Point point1 = Point.at(lat, lng);
    lat = Coordinate.fromDegrees(destinationLatitude);
    lng = Coordinate.fromDegrees(departureLongitude);
        Point point2 = Point.at(lat, lng);
        var currentDistance = EarthCalc.gcd.distance(point2, point1) / 1000;
        return BigDecimal.valueOf(currentDistance);
    }

    @Override
    public BigDecimal getDepartureLatitude() {
        return BigDecimal.valueOf(departureLatitude);
    }

    @Override
    public BigDecimal getDepartureLongitude() {
        return BigDecimal.valueOf(departureLongitude);
    }

    @Override
    public BigDecimal getDestinationLongitude() {
        return BigDecimal.valueOf(destinationLongitude);
    }

    @Override
    public BigDecimal getDestinationLatitude() {
        return BigDecimal.valueOf(destinationLatitude);
    }

    @Override
    public BigDecimal getMinimalDistance() {
        return minimalDistance;
    }

    @Override
    public Price costPerVolume() {
    return new Price(costPrVolume, currencyFactory.create("RUB"));
    }
}
