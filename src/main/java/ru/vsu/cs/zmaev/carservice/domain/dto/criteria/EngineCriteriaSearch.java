package ru.vsu.cs.zmaev.carservice.domain.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса EngineCriteriaSearch")
public class EngineCriteriaSearch {
    @Schema(description = "Id двигателя", example = "1")
    private final Long id;
    @Schema(description = "Название двигателя", example = "EP6")
    private final String name;
    @Schema(description = "Объем двигателя", example = "1.6")
    private final String capacity;
}
