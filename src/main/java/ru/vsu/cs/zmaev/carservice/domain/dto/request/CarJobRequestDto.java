package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class CarJobRequestDto {
    @Nullable
    private final Long jobTypeId;

    @Nullable
    private final Long carConfigId;
}
