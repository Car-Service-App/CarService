package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import lombok.Data;

@Data
public class CarConfigResponseDto {
    private Long id;
    private Long engineId;
    private Long transmissionId;
}
