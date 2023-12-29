package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса EngineRequestDto")
public class EngineRequestDto {
    @Schema(description = "Название двигателя", example = "EP6")
    private final String name;
    @Schema(description = "Объем двигателя", example = "1.6")
    private final String capacity;
}
