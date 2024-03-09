package ru.vsu.cs.zmaev.carservice.domain.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarModelCriteriaSearch")
public class CarModelCriteriaSearch {
    private final Long id;
    @Schema(description = "Модель автомобиля", example = "Civic")
    @Nullable
    private final String modelName;
    @Schema(description = "Производитель")
    @Nullable
    private final String manufacturerName;
}
