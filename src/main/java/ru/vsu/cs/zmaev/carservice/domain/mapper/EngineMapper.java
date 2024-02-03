package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.EngineRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.EngineResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.Engine;

@Mapper(componentModel = "spring")
public interface EngineMapper extends EntityMapper<Engine, EngineRequestDto, EngineResponseDto> {
    Engine toEntity(EngineRequestDto engineRequestDto);
    EngineResponseDto toDto(Engine engine);
}
