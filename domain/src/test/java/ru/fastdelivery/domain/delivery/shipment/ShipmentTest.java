package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.location.Departure;
import ru.fastdelivery.domain.common.location.Destination;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);

        var package1 = new Pack(weight1, new Volume(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3)),
                new Departure(BigDecimal.valueOf(45), BigDecimal.valueOf(50)),
                new Destination(BigDecimal.valueOf(55), BigDecimal.valueOf(60)));
        var package2 = new Pack(weight2, new Volume(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4)),
                new Departure(BigDecimal.valueOf(46), BigDecimal.valueOf(51)),
                new Destination(BigDecimal.valueOf(56), BigDecimal.valueOf(61)));

        var packages = List.of(package1, package2);
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

        var weightOfShipment = shipment.weightAllPackages();

        assertThat(weightOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }

    @Test
    void whenCalculatingVolumeOfAllPackages_thenReturnSum() {
        var volume1 = new Volume(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3));
        var volume2 = new Volume(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));

        var package1 = new Pack(new Weight(BigInteger.TEN), volume1,
                new Departure(BigDecimal.valueOf(45), BigDecimal.valueOf(50)),
                new Destination(BigDecimal.valueOf(55), BigDecimal.valueOf(60)));
        var package2 = new Pack(new Weight(BigInteger.ONE), volume2,
                new Departure(BigDecimal.valueOf(46), BigDecimal.valueOf(51)),
                new Destination(BigDecimal.valueOf(56), BigDecimal.valueOf(61)));

        var packages = List.of(package1, package2);
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

        var volumeOfShipment = shipment.volumeAllPackages();

        assertThat(volumeOfShipment).isEqualTo(volume1.add(volume2));
    }
}