package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCaseVolume {
    private final VolumePriceProvider volumePriceProvider;

    public Price calc(Shipment shipment){
        var volumeAllPackagesV = shipment.volumeAllPackages().volumes();
        var volumePrice = volumePriceProvider.costPerVolume().multiply(volumeAllPackagesV);
        return volumePriceProvider.costPerVolume().multiply(volumeAllPackagesV).max(volumePrice);
    }
}
