package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import ru.fastdelivery.presentation.api.request.locationRequest.*;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
    @Schema(description = "Список упаковок отправления", example = "[{\"weight\": 4056.45}]")
        @NotNull
        @NotEmpty
        List<CargoPackage> packages,
    @Schema(description = "Трехбуквенный код валюты", example = "RUB") @NotNull String currencyCode,
    @Schema(description = "Координаты отправления") DepartureReq departureReq,
    @Schema(description = "Координаты назначения") DestinationReq destinationReq) {}
