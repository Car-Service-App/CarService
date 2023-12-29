package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarModelRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarModelResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarModel;

@Mapper(componentModel = "spring")
public interface CarModelMapper extends EntityMapper<CarModel, CarModelRequestDto, CarModelResponseDto> {
    CarModel toEntity(CarModelRequestDto carModelRequestDto);
    CarModelResponseDto toDto(CarModel carModel);
}
