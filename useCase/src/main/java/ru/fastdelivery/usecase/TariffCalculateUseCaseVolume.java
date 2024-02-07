package ru.fastdelivery.usecase;

import javax.inject.Named;
import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCaseVolume {
  private final VolumePriceProvider volumePriceProvider;

  public Price calc(Shipment shipment) {
    var volumeAllPackagesV = shipment.volumeAllPackages().volumes();
    var volumePrice = volumePriceProvider.costPerVolume().multiply(volumeAllPackagesV);
    return volumePriceProvider.costPerVolume().multiply(volumeAllPackagesV).max(volumePrice);
  }
}
