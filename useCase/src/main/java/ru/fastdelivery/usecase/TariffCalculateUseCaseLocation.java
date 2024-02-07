package ru.fastdelivery.usecase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.inject.Named;
import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCaseLocation {
    private final LocationPriceProvider locationPriceProvider;

  public Price calc(Shipment shipment) {
    BigDecimal priceEveryMinDistance =
        locationPriceProvider.distanceCalc().divide(getMinimalDistance(), RoundingMode.HALF_UP);
    var minimalPrice = locationPriceProvider.minimalPrice();
    if (locationPriceProvider.distanceCalc().compareTo(getMinimalDistance()) < 0) {
      return locationPriceProvider.costPerKm().multiply(BigDecimal.ZERO);
    } else {
        return locationPriceProvider.costPerKm().multiply(priceEveryMinDistance).multiply(minimalPrice.amount());
    }
        }
    public Price minimalPrice(){
        return locationPriceProvider.minimalPrice();
    }
    public BigDecimal distanceCalc(){
        return locationPriceProvider.distanceCalc();
    }
    public  BigDecimal getDepartureLatitude(){
        return locationPriceProvider.getDepartureLatitude();
    }
    public BigDecimal getDepartureLongitude(){
        return locationPriceProvider.getDepartureLongitude();
    }
    public BigDecimal getDestinationLatitude(){
        return locationPriceProvider.getDestinationLatitude();
    }
    public BigDecimal getDestinationLongitude(){
        return locationPriceProvider.getDestinationLongitude();
    }
    public BigDecimal getMinimalDistance(){
        return locationPriceProvider.getMinimalDistance();
    }
}

