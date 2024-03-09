package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CarJobResponseDto {
    private final Long id;
    private final CarConfigResponseDto carConfig;
    private final JobTypeResponseDto jobType;
    private final CarResponseDto car;
    private final Long mileage;
    private final Integer time;
}
