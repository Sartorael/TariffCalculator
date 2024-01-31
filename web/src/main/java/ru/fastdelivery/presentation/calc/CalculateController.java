package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.TariffCalculateUseCaseVolume;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;
    private final TariffCalculateUseCaseVolume tariffCalculateUseCaseVolume;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {

        List<Pack> packs = new ArrayList<>();
        for (CargoPackage cargoPackage : request.packages()) {
            Weight weight = new Weight(cargoPackage.weight());
            Volume volume = new Volume(cargoPackage.length(), cargoPackage.width(), cargoPackage.height());
            Pack pack = new Pack(weight, volume);
            packs.add(pack);
        }

        var shipment = new Shipment(packs, currencyFactory.create(request.currencyCode()));
        var minimalPrice = tariffCalculateUseCase.minimalPrice();
        var totalPrice = tariffCalculateUseCase.calc(shipment).amount()
                .add(tariffCalculateUseCaseVolume.calc(shipment).amount());
        var calculatedPriceResponse = new Price(totalPrice, minimalPrice.currency());
        return new CalculatePackagesResponse(calculatedPriceResponse, minimalPrice);
    }
}

