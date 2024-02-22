package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.vsu.cs.zmaev.carservice.domain.enums.ProductionType;

@Data
@Schema(description = "Описание класса ManufacturerRequestDto")
public class ManufacturerRequestDto {
    @Schema(description = "Название брэнда", example = "Honda")
    private final String brandName;
    @Schema(description = "Страна производителя", example = "Япония")
    private final String country;
    @Schema(description = "Тип производимой продукции", example = "ALL", enumAsRef = true)
    private final ProductionType productionType;
    @Schema(description = "Ссылка на изображение")
    private final String logoLink;
}
