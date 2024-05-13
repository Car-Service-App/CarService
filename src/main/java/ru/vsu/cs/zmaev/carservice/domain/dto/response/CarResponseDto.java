package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarResponseDto")
public class CarResponseDto {
    private Long id;
    private String manufacturerName;
    private String modelName;
    private Integer releaseYear;
    private String type;
    private String country;
//    @Schema(description = "Id автомобиля", example = "1")
//    Long id;
//    @Schema(description = "Тип автомобиля", example = "CABRIOLET")
//    private final CarType carType;
//    @Schema(description = "Год выпуска автомобиля")
//    private final Integer releaseYear;
//    @Schema(description = "Модель автомобиля")
//    private final CarModelResponseDto carModel;
//    @Schema(description = "Двигатели данной модели")
//    private List<CarConfigResponseDto> carConfigs;
//    @Schema(description = "Ссылка на изображение автомобиля")
//    private final String carImageLink;
}
