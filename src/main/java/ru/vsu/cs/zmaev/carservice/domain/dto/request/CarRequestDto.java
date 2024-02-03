package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.vsu.cs.zmaev.carservice.domain.enums.CarType;

import java.time.Instant;

@Data
@Schema(description = "Описание класса CarRequestDto")
public class CarRequestDto {
    @Schema(description = "Тип автомобиля", example = "CABRIOLET")
    private final CarType carType;
    @Schema(description = "Год выпуска автомобиля")
    private final Instant releaseYear;
    @Schema(description = "Модель автомобиля", example = "1")
    private final Long carModelId;
    @Schema(description = "Id Двигателя автомобиля", example = "1")
    private final Long engineId;
    @Schema(description = "Id Трансмиссии автомобиля", example = "1")
    private final Long transmissionId;
    @Schema(description = "Id Трансмиссии автомобиля", example = "cars/bmw/m3.img")
    private final String carImageLink;
}
