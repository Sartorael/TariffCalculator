package ru.fastdelivery.properties.provider;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("location.departure")
@Setter
public class DepartureProperties {
  @DecimalMin("45")
  @DecimalMax("65")
  private BigDecimal latitude;

  @DecimalMin("30")
  @DecimalMax("96")
  private BigDecimal longitude;
}
