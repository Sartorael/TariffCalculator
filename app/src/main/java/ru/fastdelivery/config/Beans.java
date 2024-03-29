package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.usecase.*;

/** Определение реализаций бинов для всех модулей приложения */
@Configuration
public class Beans {

  @Bean
  public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
    return new CurrencyFactory(currencyProperties);
  }

  @Bean
  public TariffCalculateUseCaseVolume tariffCalculateUseCaseVolume(
      VolumePriceProvider volumePriceProvider) {
    return new TariffCalculateUseCaseVolume(volumePriceProvider);
  }

  @Bean
  public TariffCalculateUseCase tariffCalculateUseCase(WeightPriceProvider weightPriceProvider) {
    return new TariffCalculateUseCase(weightPriceProvider);
  }

  @Bean
  public TariffCalculateUseCaseLocation tariffCalculateUseCaseLocation(
      LocationPriceProvider locationPriceProvider) {
    return new TariffCalculateUseCaseLocation(locationPriceProvider);
  }
}
