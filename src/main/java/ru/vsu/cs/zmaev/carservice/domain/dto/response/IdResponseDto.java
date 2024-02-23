package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса IdResponseDto")
public class IdResponseDto {
    @Schema(description = "Id ответа", example = "1")
    Long id;
}
