package ru.vsu.cs.zmaev.carservice.domain.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarCriteriaSearch")
public class CarCriteriaSearch {
    private final Long id;
    @Schema(description = "Тип автомобиля", example = "CABRIOLET")
    private final String carType;
    @Schema(description = "Год выпуска автомобиля")
    private final Integer releaseYear;
    @Schema(description = "Название производителя автомобиля", example = "Honda")
    private final String brandName;
    @Schema(description = "Название модели автомобиля", example = "Civic")
    private final String carModelName;
    @Schema(description = "Id Двигателя автомобиля", example = "1")
    private final Long engineId;
    @Schema(description = "Название двигателя автомобиля", example = "EP6")
    private final String engineName;
    @Schema(description = "Объем двигателя автомобиля", example = "1.6")
    private final String engineCapacity;
    @Schema(description = "Id Трансмиссии автомобиля", example = "1")
    private final Long transmissionId;
    @Schema(description = "Название трансмиссии автомобиля", example = "RWD")
    private final String transmission;
}
