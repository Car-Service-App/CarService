package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.vsu.cs.zmaev.carservice.domain.entity.Engine;
import ru.vsu.cs.zmaev.carservice.domain.enums.CarType;
import ru.vsu.cs.zmaev.carservice.domain.enums.TransmissionType;

import java.time.Instant;
import java.util.List;

@Data
@Schema(description = "Описание класса CarResponseDto")
public class CarResponseDto {
    @Schema(description = "Id автомобиля", example = "1")
    Long id;
    @Schema(description = "Тип автомобиля", example = "CABRIOLET")
    private final CarType carType;
    @Schema(description = "Год выпуска автомобиля")
    private final Integer releaseYear;
    @Schema(description = "Модель автомобиля")
    private final CarModelResponseDto carModel;
    @Schema(description = "Трансмиссия автомобиля", example = "RWD")
    private TransmissionType transmission;
    @Schema(description = "Двигатели данной модели")
    private List<EngineResponseDto> engines;
    @Schema(description = "Ссылка на изображение автомобиля")
    private final String carImageLink;
}
