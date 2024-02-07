package ru.fastdelivery.domain.delivery.shipment;

import java.math.BigDecimal;
import java.util.List;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(List<Pack> packages, Currency currency) {
  public Weight weightAllPackages() {
    return packages.stream().map(Pack::weight).reduce(Weight.zero(), Weight::add);
  }

  public Volume volumeAllPackages() {
    return packages.stream()
        .map(Pack::volume)
        .reduce(new Volume(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), Volume::add);
  }
}
