package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarJob;

@Mapper(componentModel = "spring")
public interface CarJobMapper extends EntityMapper<CarJob, CarJobRequestDto, CarJobResponseDto> {
    @Override
    CarJob toEntity(CarJobRequestDto request);

    @Override
    CarJobResponseDto toDto(CarJob entity);
}
