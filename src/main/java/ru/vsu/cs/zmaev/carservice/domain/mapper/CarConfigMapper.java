package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarConfigRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarConfigResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarConfig;

@Mapper(componentModel = "spring", uses = { CarModelMapper.class, EngineMapper.class})
public interface CarConfigMapper extends EntityMapper<CarConfig, CarConfigRequestDto, CarConfigResponseDto> {
    @Override
    CarConfigResponseDto toDto(CarConfig entity);
}
