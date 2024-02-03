package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.ManufacturerRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.ManufacturerResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.Manufacturer;

@Mapper(componentModel = "spring")
public interface ManufacturerMapper extends EntityMapper<Manufacturer, ManufacturerRequestDto, ManufacturerResponseDto> {

    Manufacturer toEntity(ManufacturerRequestDto request);
    ManufacturerResponseDto toDto(Manufacturer entity);
    ManufacturerResponseDto toResponse(ManufacturerRequestDto request);
}