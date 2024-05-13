package ru.vsu.cs.zmaev.carservice.domain.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarWithConfigResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.Car;

@Mapper(componentModel = "spring", uses = { CarModelMapper.class, EngineMapper.class, CarConfigMapper.class})
public interface CarMapper extends EntityMapper<Car, CarRequestDto, CarResponseDto> {

    Car toEntity(CarRequestDto carRequestDto);

    default CarResponseDto toDto(Car car) {
        if (car == null) {
            return null;
        }

        CarResponseDto carResponseDto = new CarResponseDto();
        carResponseDto.setId(car.getId());
        carResponseDto.setManufacturerName(car.getCarModel().getManufacturer().getBrandName());
        carResponseDto.setModelName(car.getCarModel().getModelName());
        carResponseDto.setReleaseYear(car.getReleaseYear());
        carResponseDto.setType(car.getCarType().name());
        carResponseDto.setCountry(car.getCarModel().getManufacturer().getCountry());

        return carResponseDto;
    }

    default CarWithConfigResponseDto toCarWithConfigDto(Car car, CarConfigMapper carConfigMapper) {
        if (car == null) {
            return null;
        }

        CarWithConfigResponseDto carResponseDto = new CarWithConfigResponseDto();
        carResponseDto.setId(car.getId());
        carResponseDto.setManufacturerName(car.getCarModel().getManufacturer().getBrandName());
        carResponseDto.setModelName(car.getCarModel().getModelName());
        carResponseDto.setReleaseYear(car.getReleaseYear());
        carResponseDto.setType(car.getCarType().name());
        carResponseDto.setCountry(car.getCarModel().getManufacturer().getCountry());
        carResponseDto.setCarGeneration(car.getGeneration());
        carResponseDto.setCarImageLink(car.getCarImageLink());
        carResponseDto.setIsRestyling(car.getIsRestyling());
        carResponseDto.setConfig(car.getCarConfigs().stream().map(carConfigMapper::toDto).toList());

        return carResponseDto;
    }
}
