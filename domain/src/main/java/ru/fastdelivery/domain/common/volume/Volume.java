package ru.fastdelivery.domain.common.volume;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Общий класс обьёма
 *
 * @param length длина в мм
 * @param width ширина в мм
 * @param height высота в мм
 */
public record Volume(BigDecimal length, BigDecimal width, BigDecimal height)
    implements Comparable<Volume> {

  public Volume {
    int zero = 0;
    int maxVolume = 1500;
    int scale = 50;
    BigDecimal firstLength = length;
    BigDecimal firstWidth = width;
    BigDecimal firstHeight = height;

    if (length.compareTo(BigDecimal.ZERO) < zero
        || width.compareTo(BigDecimal.ZERO) < zero
        || height.compareTo(BigDecimal.ZERO) < zero) {
      throw new IllegalArgumentException("Parameters must not be negative!");
    }

    length =
        length
            .setScale(zero, RoundingMode.HALF_UP)
            .divide(BigDecimal.valueOf(scale), zero, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(scale));
    width =
        width
            .setScale(zero, RoundingMode.HALF_UP)
            .divide(BigDecimal.valueOf(scale), zero, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(scale));
    height =
        height
            .setScale(zero, RoundingMode.HALF_UP)
            .divide(BigDecimal.valueOf(scale), zero, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(scale));

    BigDecimal sum = firstLength.add(firstWidth).add(firstHeight);
    if (sum.compareTo(BigDecimal.valueOf(maxVolume)) > zero) {
      throw new IllegalArgumentException("Sum of parameters must not exceed " + maxVolume + "!");
    }
  }

  public Volume add(Volume additionalVolume) {
    BigDecimal newLength = this.length.add(additionalVolume.length);
    BigDecimal newWidth = this.width.add(additionalVolume.width);
    BigDecimal newHeight = this.height.add(additionalVolume.height);
    return new Volume(newLength, newWidth, newHeight);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Volume volume = (Volume) o;
    return length.compareTo(volume.length) == 0
        && width.compareTo(volume.width) == 0
        && height.compareTo(volume.height) == 0;
  }

  public BigDecimal volumes() {
    BigDecimal volume =
        length
            .multiply(width)
            .multiply(height)
            .setScale(9, RoundingMode.HALF_UP)
            .divide(BigDecimal.valueOf(1_000_000_000), RoundingMode.HALF_UP)
            .setScale(4, RoundingMode.HALF_UP);
    return volume
        .divide(BigDecimal.valueOf(0.05), RoundingMode.HALF_UP)
        .setScale(0, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(0.05));
  }

  @Override
  public int compareTo(Volume v) {
    BigDecimal volume1 = length.multiply(width).multiply(height);
    BigDecimal volume2 = v.length.multiply(v.width).multiply(v.height);
    return volume1.compareTo(volume2);
  }

  public boolean greaterThan(Volume v) {
    BigDecimal volume1 = length.multiply(width).multiply(height);
    BigDecimal volume2 = v.length.multiply(v.width).multiply(v.height);
    return volume1.compareTo(volume2) > 0;
  }

  @Override
  public BigDecimal length() {
    return length;
  }

  @Override
  public BigDecimal width() {
    return width;
  }

  @Override
  public BigDecimal height() {
    return height;
  }

  public boolean widthGreaterThan(Volume v) {
    return width.compareTo(v.width()) > 0;
  }

  public boolean lengthGreaterThan(Volume v) {
    return length.compareTo(v.length()) > 0;
  }

  public boolean heightGreaterThan(Volume v) {
    return height.compareTo(v.height()) > 0;
  }
}
