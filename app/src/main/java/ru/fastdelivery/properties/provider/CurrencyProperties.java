package ru.fastdelivery.properties.provider;

import java.util.List;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;

/**
 * Настройки валют из конфига
 */
@Configuration
@ConfigurationProperties("currency")
@Setter
public class CurrencyProperties implements CurrencyPropertiesProvider {

    private List<String> available;

    @Override
    public boolean isAvailable(String code) {
        return available.contains(code);
    }
}
