package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.JobTypeRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.JobTypeResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.JobType;

@Mapper(componentModel = "spring")
public interface JobTypeMapper extends EntityMapper<JobType, JobTypeRequestDto, JobTypeResponseDto> {
    @Override
    JobType toEntity(JobTypeRequestDto request);

    @Override
    JobTypeResponseDto toDto(JobType entity);
}
