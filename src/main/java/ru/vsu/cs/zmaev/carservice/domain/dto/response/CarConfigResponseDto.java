package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import lombok.Data;
import ru.vsu.cs.zmaev.carservice.domain.enums.TransmissionType;

@Data
public class CarConfigResponseDto {
    private Long engineId;
    private Long transmissionId;
}
