package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CarJobRequestDto {
    private final Long jobTypeId;
    private final Long carConfigId;
    private final Long mileage;
    private final Integer time;
    private final List<CarPartsCreateCarJobRequestDto> carParts;
}
