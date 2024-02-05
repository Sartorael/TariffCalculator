package ru.fastdelivery.domain.delivery.shipment;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.common.location.Departure;
import ru.fastdelivery.domain.common.location.Destination;
import ru.fastdelivery.domain.delivery.pack.Pack;
import java.math.BigDecimal;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }
    public Volume volumeAllPackages(){
        return packages.stream().map(Pack::volume).reduce(new Volume(BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO), Volume::add);
    }


    public Departure departureAllPackages(){
        return packages.stream().map(Pack::departure).reduce(new Departure(BigDecimal.ZERO, BigDecimal.ZERO),
                Departure::add);
    }
    public Destination destinationAllPackages(){
        return packages.stream().map(Pack::destination).reduce(new Destination(BigDecimal.ZERO, BigDecimal.ZERO),
                Destination::add);
    }

}
