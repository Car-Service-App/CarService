package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import lombok.Data;

@Data
public class PartsInJobRequestDto {
    private Long carJobId;
    private Long carPartId;
}
