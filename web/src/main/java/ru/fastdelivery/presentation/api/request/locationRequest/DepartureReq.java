package ru.fastdelivery.presentation.api.request.locationRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Координаты отправления")
public record DepartureReq(
    @Schema(description = "Широта", example = "55.446008") BigDecimal latitude,
    @Schema(description = "Долгота", example = "65.339151") BigDecimal longitude) {}
