package ru.vsu.cs.zmaev.carservice.domain.dto.request;

import lombok.Data;

@Data
public class CarPartsCreateCarJobRequestDto {
    private final Long carPartTypeId;
    private final String oem;
    private final Integer amount;
}
