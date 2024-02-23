package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.PartsInJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.PartsInJobResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.PartsInJob;

@Mapper(componentModel = "spring")
public interface PartsInJobMapper extends EntityMapper<PartsInJob, PartsInJobRequestDto, PartsInJobResponseDto> {
    @Override
    PartsInJob toEntity(PartsInJobRequestDto request);

    @Override
    @Mapping(source = "carJob.id", target = "carJobId")
    PartsInJobResponseDto toDto(PartsInJob entity);
}