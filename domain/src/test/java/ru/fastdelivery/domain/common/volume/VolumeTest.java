package ru.fastdelivery.domain.common.volume;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class VolumeTest {

  @Test
  public void testVolumeCreation() {
    Volume volume = new Volume(new BigDecimal("10"), new BigDecimal("20"), new BigDecimal("30"));
    assertEquals(new BigDecimal("10"), volume.length());
    assertEquals(new BigDecimal("20"), volume.width());
    assertEquals(new BigDecimal("30"), volume.height());
  }

  @Test
  public void testVolumeAddition() {
    Volume volume1 = new Volume(new BigDecimal("10"), new BigDecimal("10"), new BigDecimal("10"));
    Volume volume2 = new Volume(new BigDecimal("5"), new BigDecimal("5"), new BigDecimal("5"));
    Volume expected = new Volume(new BigDecimal("15"), new BigDecimal("15"), new BigDecimal("15"));
    assertEquals(expected, volume1.add(volume2));
  }

  @Test
  public void testVolumeComparison() {
    Volume smallerVolume =
        new Volume(new BigDecimal("5"), new BigDecimal("5"), new BigDecimal("5"));
    Volume largerVolume =
        new Volume(new BigDecimal("10"), new BigDecimal("10"), new BigDecimal("10"));
    assertTrue(largerVolume.greaterThan(smallerVolume));
  }
}
