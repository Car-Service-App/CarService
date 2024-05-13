package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CarWithConfigResponseDto {
    private Long id;
    private String manufacturerName;
    private String modelName;
    private Integer releaseYear;
    private String type;
    private String country;
    private Integer carGeneration;
    private Boolean isRestyling;
    private String carImageLink;
    private List<CarConfigResponseDto> config;
}
