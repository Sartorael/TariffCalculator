package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.common.volume.Volume;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 * @param volume объем упаковки
 */
public record Pack(Weight weight,Volume volume) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));
    private static final  int max = 1500;
    private static final Volume maxVolume = new Volume(
            BigDecimal.valueOf(500), BigDecimal.valueOf(500), BigDecimal.valueOf(500)
    );

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
        if (volume.greaterThan(maxVolume) || volume.compareTo(maxVolume) > 0) {
            throw new IllegalArgumentException("Package volume cannot exceed " + max);
        }
        if (volume.widthGreaterThan(maxVolume)){
            throw new IllegalArgumentException("Package Width cannot exceed " + max);
        }
        if (volume.lengthGreaterThan(maxVolume)){
            throw new IllegalArgumentException("Package Length cannot exceed " + max);
        }
        if (volume.heightGreaterThan(maxVolume)){
            throw new IllegalArgumentException("Package Height cannot exceed " + max);
        }
    }
}
