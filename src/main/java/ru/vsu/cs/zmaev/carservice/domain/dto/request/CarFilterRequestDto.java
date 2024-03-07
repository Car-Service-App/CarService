package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarFilterRequestDto")
public class CarFilterRequestDto {

    @Nullable
    @Schema(description = "Тип автомобиля", example = "SUV")
    private String carType;

    @Nullable
    @Schema(description = "Год выпуска автомобиля", example = "2022")
    private Integer releaseYear;

    @Nullable
    @Schema(description = "Название бренда автомобиля", example = "Toyota")
    private String brandName;

    @Nullable
    @Schema(description = "Название модели автомобиля", example = "RAV4")
    private String carModelName;

    @Nullable
    @Schema(description = "Идентификатор двигателя")
    private Long engineId;

    @Nullable
    @Schema(description = "Название двигателя", example = "EP6")
    private String engineName;

    @Nullable
    @Schema(description = "Объем двигателя", example = "1.6")
    private String engineCapacity;

    @Nullable
    @Schema(description = "Идентификатор трансмиссии")
    private Long transmissionId;

    @Nullable
    @Schema(description = "Название трансмиссии", example = "Automatic")
    private String transmissionName;
}
