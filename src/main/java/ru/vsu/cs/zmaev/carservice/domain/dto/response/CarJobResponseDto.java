package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CarJobResponseDto {
    private final Long id;
    private final Long engineId;
    private final JobTypeResponseDto jobType;
    private final CarResponseDto car;
}
