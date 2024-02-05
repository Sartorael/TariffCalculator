package ru.fastdelivery.properties.provider;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties("location.destination")
@Setter
public class DestinationProperties {
   @DecimalMin("45")
   @DecimalMax("65")
   private BigDecimal latitude;

   @DecimalMin("30")
   @DecimalMax("96")
   private BigDecimal longitude;

}
