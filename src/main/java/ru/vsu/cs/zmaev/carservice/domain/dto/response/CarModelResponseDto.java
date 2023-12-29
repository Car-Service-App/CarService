package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarModelResponseDto")
public class CarModelResponseDto {
    private final Long id;
    @Schema(description = "Модель автомобиля", example = "Civic")
    private final String modelName;
    @Schema(description = "Производитель")
    private final ManufacturerResponseDto manufacturer;
}
