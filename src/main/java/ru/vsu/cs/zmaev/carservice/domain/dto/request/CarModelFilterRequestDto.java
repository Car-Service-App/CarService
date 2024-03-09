package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarModelFilterRequestDto {

    @Size(max = 255)
    @Nullable
    private String modelName;

    @Size(max = 255)
    @Nullable
    private String manufacturerName;
}
