package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.Car;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarModel;
import ru.vsu.cs.zmaev.carservice.domain.entity.Engine;
import ru.vsu.cs.zmaev.carservice.domain.entity.Transmission;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.CarModelRepository;
import ru.vsu.cs.zmaev.carservice.repository.CarRepository;
import ru.vsu.cs.zmaev.carservice.repository.EngineRepository;
import ru.vsu.cs.zmaev.carservice.repository.TransmissionRepository;
import ru.vsu.cs.zmaev.carservice.repository.criteria.CriteriaRepository;
import ru.vsu.cs.zmaev.carservice.service.CarService;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final EngineRepository engineRepository;
    private final TransmissionRepository transmissionRepository;
    private final CriteriaRepository<Car, CarCriteriaSearch> carCriteriaRepository;
    private final CarMapper carMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CarResponseDto> findAllWithFilters(EntityPage entityPage, CarCriteriaSearch carCriteriaSearch) {
        return carCriteriaRepository.findAllWithFilters(entityPage, carCriteriaSearch).map(carMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDto findOneById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(Car.class, id));
        return carMapper.toDto(car);
    }

    @Override
    @Transactional
    public CarResponseDto save(CarRequestDto carRequestDto) {
        Car car = carMapper.toEntity(carRequestDto);
        CarModel carModel = carModelRepository.findById(carRequestDto.getCarModelId()).orElseThrow(() ->
                new NoSuchEntityException(CarModel.class, carRequestDto.getCarModelId()));
        Engine engine = engineRepository.findById(carRequestDto.getEngineId()).orElseThrow(() ->
                new NoSuchEntityException(Engine.class, carRequestDto.getEngineId()));
        Transmission transmission = transmissionRepository.findById(carRequestDto.getTransmissionId()).orElseThrow(() ->
                new NoSuchEntityException(Transmission.class, carRequestDto.getTransmissionId()));
        car.setCarModel(carModel);
        car.setEngine(engine);
        car.setTransmission(transmission);
        return carMapper.toDto(carRepository.save(car));
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
        carRepository.delete(car);
    }
}
