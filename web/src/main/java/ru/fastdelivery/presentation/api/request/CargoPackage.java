package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,
        @Schema(description = "Длина упаковки, мм", example = "560")
        BigDecimal length,
        @Schema(description = "Ширина упаковки, мм", example = "100")
        BigDecimal width,
        @Schema(description = "Высота упаковки, мм", example = "200")
        BigDecimal height
) {
}
