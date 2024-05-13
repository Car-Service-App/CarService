package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ExistingCarJobResponseDto {
    private Long id;
    private Long carId;
    private Long carConfigId;
    private Long engineId;
    private Long transmissionId;
    private String jobName;
    private String manufacturerName;
    private String carModelName;
    private String engineName;
    private String engineCapacity;
    private String transmissionName;
    private Long mileage;
    private Integer time;
}
