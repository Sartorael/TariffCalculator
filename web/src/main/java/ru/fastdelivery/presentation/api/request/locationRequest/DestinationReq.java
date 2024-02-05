package ru.fastdelivery.presentation.api.request.locationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.fastdelivery.domain.common.location.Destination;

import java.math.BigDecimal;

@Schema(description = "Координаты отправления")
public record DestinationReq(
        @Schema(description = "Широта", example = "55.446008")
        Destination latitude,
        @Schema(description = "Долгота", example = "65.339151")
        Destination longitude
) {
    // Геттеры или другие методы, если необходимо
}