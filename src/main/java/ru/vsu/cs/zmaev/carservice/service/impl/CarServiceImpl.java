package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarConfigRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarConfigResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarWithConfigResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.EngineResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.*;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarConfigMapper;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarMapper;
import ru.vsu.cs.zmaev.carservice.domain.mapper.EngineMapperImpl;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.*;
import ru.vsu.cs.zmaev.carservice.repository.criteria.CriteriaRepository;
import ru.vsu.cs.zmaev.carservice.service.CarService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final CarConfigRepository carConfigRepository;
    private final EngineRepository engineRepository;
    private final TransmissionRepository transmissionRepository;
    private final CriteriaRepository<Car, CarCriteriaSearch> carCriteriaRepository;
    private final CarMapper carMapper;
    private final CarConfigMapper carConfigMapper;
    private final EngineMapperImpl engineMapperImpl;

    @Override
    @Transactional(readOnly = true)
    public Page<CarResponseDto> findAllWithFilters(EntityPage entityPage, CarCriteriaSearch carCriteriaSearch) {
        return carCriteriaRepository.findAllWithFilters(entityPage, carCriteriaSearch).map(carMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarResponseDto> findAll(Pageable pageable) {
        return carRepository.findAll(pageable).map(carMapper::toDto);
    }

    public List<CarResponseDto> findCarsByModelId(Long modelId) {
        List<Car> cars = carRepository.findCarByCarModel(modelId);
        return cars.stream().map(carMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDto findOneById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(Car.class, id));
        return carMapper.toDto(car);
    }

    @Override
    public CarWithConfigResponseDto findCarWithConfigsById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(Car.class, id));
        CarWithConfigResponseDto carWithConfigResponseDto = carMapper.toCarWithConfigDto(car, carConfigMapper);
        List<CarConfigResponseDto> carConfigResponseDtos = new ArrayList<>();
        for (CarConfig carConfig: car.getCarConfigs()) {
            EngineResponseDto engine = engineMapperImpl.toDto(
                    engineRepository.findById(carConfig.getEngineId())
                    .orElseThrow(
                            () -> new NoSuchEntityException(Engine.class, carConfig.getEngineId())));
            String transmissionName = transmissionRepository.findById(carConfig.getTransmissionId()).orElseThrow(
                    () -> new NoSuchEntityException(Transmission.class, carConfig.getTransmissionId())
            ).getTransmissionType().name();
            CarConfigResponseDto carConfigResponseDto = new CarConfigResponseDto();
            carConfigResponseDto.setId(carConfig.getId());
            carConfigResponseDto.setEngineName(engine.getName());
            carConfigResponseDto.setEngineCapacity(engine.getCapacity());
            carConfigResponseDto.setTransmissionName(transmissionName);
            carConfigResponseDtos.add(carConfigResponseDto);
        }
        carWithConfigResponseDto.setConfig(carConfigResponseDtos);
        return carWithConfigResponseDto;
    }

    @Override
    @Transactional
    public CarResponseDto save(CarRequestDto carRequestDto) {
        Car car = carMapper.toEntity(carRequestDto);
        CarModel carModel = carModelRepository.findById(carRequestDto.getCarModelId()).orElseThrow(() ->
                new NoSuchEntityException(CarModel.class, carRequestDto.getCarModelId()));
        car.setCarModel(carModel);
        car = carRepository.save(car);
        for (CarConfigRequestDto cc: carRequestDto.getCarConfigs()) {
            if (!engineRepository.existsById(cc.getEngineId())) {
                throw new NoSuchEntityException(Engine.class, cc.getEngineId());
            }
            if (!transmissionRepository.existsById(cc.getTransmissionId())) {
                throw new NoSuchEntityException(Transmission.class, cc.getTransmissionId());
            }
            CarConfig carConfig = new CarConfig();
            carConfig.setCar(car);
            carConfig.setEngineId(cc.getEngineId());
            carConfig.setTransmissionId(cc.getTransmissionId());
            carConfigRepository.save(carConfig);
        }
        return carMapper.toDto(car);
    }

    @Override
    @Transactional
    public CarResponseDto update(Long id, CarRequestDto carRequestDto) {
        return carRepository
                .findById(id)
                .map(existingEvent -> {
                    carMapper.partialUpdate(existingEvent, carRequestDto);
                    return existingEvent;
                })
                .map(carRepository::save)
                .map(carMapper::toDto)
                .orElseThrow(() -> new NoSuchEntityException(CarModel.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(Car.class, id));
        carConfigRepository.deleteByCarId(car.getId());
        carRepository.delete(car);
    }
}
