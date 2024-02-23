package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import lombok.Data;

@Data
public class PartsInJobResponseDto {
    private Long id;
    private Long carJobId;
    private Long carPartId;
}
