package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCarPartInCarPartServiceRequestDto {
    @Schema(description = "Id производителя")
    private final Long carId;
    @Schema(description = "Название категории")
    private final Long carPartTypeId;
    @Schema(description = "OEM номер детали")
    private final String oem;
    @Schema(description = "Ссылка на изображение детали")
    private final String carPartImage;
}
