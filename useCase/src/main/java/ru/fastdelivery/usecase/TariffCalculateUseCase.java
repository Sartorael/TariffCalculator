package ru.fastdelivery.usecase;

import javax.inject.Named;
import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
  private final WeightPriceProvider weightPriceProvider;

  public Price calc(Shipment shipment) {
    var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
    var minimalPrice = weightPriceProvider.minimalPrice();

    return weightPriceProvider.costPerKg().multiply(weightAllPackagesKg).max(minimalPrice);
  }

  public Price minimalPrice() {
    return weightPriceProvider.minimalPrice();
  }
}
