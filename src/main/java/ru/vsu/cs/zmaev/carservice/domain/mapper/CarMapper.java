package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.Car;

@Mapper(componentModel = "spring", uses = { CarModelMapper.class, EngineMapper.class})
public interface CarMapper extends EntityMapper<Car, CarRequestDto, CarResponseDto> {

    Car toEntity(CarRequestDto carRequestDto);

    @Mapping(source = "transmission.transmissionType", target = "transmission")
    CarResponseDto toDto(Car car);
}
