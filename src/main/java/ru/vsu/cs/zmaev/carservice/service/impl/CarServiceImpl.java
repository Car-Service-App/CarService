package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.*;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.*;
import ru.vsu.cs.zmaev.carservice.repository.criteria.CriteriaRepository;
import ru.vsu.cs.zmaev.carservice.service.CarService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final CarEngineRepository carEngineRepository;
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
    public Page<CarResponseDto> findAll(Pageable pageable) {
        return new PageImpl<>(carRepository.findAll()).map(carMapper::toDto);
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
    @Transactional
    public CarResponseDto save(CarRequestDto carRequestDto) {
        Car car = carMapper.toEntity(carRequestDto);
        CarModel carModel = carModelRepository.findById(carRequestDto.getCarModelId()).orElseThrow(() ->
                new NoSuchEntityException(CarModel.class, carRequestDto.getCarModelId()));
        Transmission transmission = transmissionRepository.findById(carRequestDto.getTransmissionId()).orElseThrow(() ->
                new NoSuchEntityException(Transmission.class, carRequestDto.getTransmissionId()));
        if (!carRequestDto.getEnginesId().isEmpty()) {
            for (Long id: carRequestDto.getEnginesId()) {
                engineRepository.findById(id).orElseThrow(() -> new NoSuchEntityException(Engine.class, id));
            }
            for (Long id: carRequestDto.getEnginesId()){
                CarEngine carEngine = new CarEngine();
                carEngine.setCarId(carRequestDto.getCarModelId());
                carEngine.setEngineId(id);
                carEngineRepository.save(carEngine);
            }
        }
        car.setCarModel(carModel);
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
