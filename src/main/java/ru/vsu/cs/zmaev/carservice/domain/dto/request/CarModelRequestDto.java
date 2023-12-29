package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarModelRequestDto")
public class CarModelRequestDto {
    @Schema(description = "Модель автомобиля", example = "Civic")
    private final String modelName;
    @Schema(description = "Название производителя", example = "Honda")
    private final String manufacturerName;
}
