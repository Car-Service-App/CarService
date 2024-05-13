package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.AllCarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.ExistingCarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarJob;

@Mapper(componentModel = "spring")
public interface CarJobMapper extends EntityMapper<CarJob, CarJobRequestDto, ExistingCarJobResponseDto> {
    @Override
    CarJob toEntity(CarJobRequestDto request);

    @Override
    ExistingCarJobResponseDto toDto(CarJob entity);

    @Mapping(source = "jobType.id", target = "jobTypeId")
    @Mapping(source = "jobType.jobName", target = "jobTypeName")
    @Mapping(source = "carConfig.id", target = "carConfigId")
    @Mapping(target = "isExist", ignore = true)
    AllCarJobResponseDto toResponse(CarJob entity);
}
