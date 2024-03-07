package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import lombok.Data;

@Data
public class CarJobRequestDto {
    private final Long jobTypeId;
    private final Long carConfigId;
}
