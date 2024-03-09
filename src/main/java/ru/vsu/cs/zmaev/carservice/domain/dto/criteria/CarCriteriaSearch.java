package ru.vsu.cs.zmaev.carservice.domain.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarCriteriaSearch")
public class CarCriteriaSearch {
    @Nullable
    private final Long id;
    @Schema(description = "Тип автомобиля", example = "CABRIOLET")
    @Nullable
    private final String carType;
    @Schema(description = "Год выпуска автомобиля")
    @Nullable
    private final Integer releaseYear;
    @Schema(description = "Название производителя автомобиля", example = "Honda")
    @Nullable
    private final String brandName;
    @Schema(description = "Название модели автомобиля", example = "Civic")
    @Nullable
    private final String carModelName;
    @Schema(description = "Id Двигателя автомобиля", example = "1")
    @Nullable
    private final Long engineId;
    @Schema(description = "Название двигателя автомобиля", example = "EP6")
    @Nullable
    private final String engineName;
    @Schema(description = "Объем двигателя автомобиля", example = "1.6")
    @Nullable
    private final String engineCapacity;
    @Schema(description = "Id Трансмиссии автомобиля", example = "1")
    private final Long transmissionId;
    @Nullable
    @Schema(description = "Название трансмиссии автомобиля", example = "RWD")
    private final String transmission;
}
