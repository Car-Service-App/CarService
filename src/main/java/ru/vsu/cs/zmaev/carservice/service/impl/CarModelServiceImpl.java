package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarModelCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarModelRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarModelResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarModel;
import ru.vsu.cs.zmaev.carservice.domain.entity.Manufacturer;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarModelMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.CarModelRepository;
import ru.vsu.cs.zmaev.carservice.repository.ManufacturerRepository;
import ru.vsu.cs.zmaev.carservice.repository.criteria.CriteriaRepository;
import ru.vsu.cs.zmaev.carservice.service.CarModelService;

@Service
@RequiredArgsConstructor
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository carModelRepository;
    private final CriteriaRepository<CarModel, CarModelCriteriaSearch> carModelCriteriaRepository;
    private final CarModelMapper carModelMapper;

    private final ManufacturerRepository manufacturerRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CarModelResponseDto> findAll(Pageable pageable) {
        return carModelRepository.findAll(pageable).map(carModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarModelResponseDto> findAllWithFilters(
            EntityPage entityPage,
            CarModelCriteriaSearch carModelCriteriaSearch) {
        return carModelCriteriaRepository
                .findAllWithFilters(entityPage, carModelCriteriaSearch)
                .map(carModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CarModelResponseDto findOneById(Long id) {
        CarModel carModel = carModelRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(CarModel.class, id));
        return carModelMapper.toDto(carModel);
    }

    @Override
    @Transactional
    public CarModelResponseDto save(CarModelRequestDto carModelRequestDto) {
        Manufacturer manufacturer = manufacturerRepository
                .findByBrandName(carModelRequestDto.getManufacturerName())
                .orElseThrow(() -> new NoSuchEntityException(
                        Manufacturer.class, carModelRequestDto.getManufacturerName()));
        CarModel carModel = carModelMapper.toEntity(carModelRequestDto);
        carModel.setManufacturer(manufacturer);
        carModelRepository.save(carModel);
        return carModelMapper.toDto(carModel);
    }

    @Override
    @Transactional
    public CarModelResponseDto update(Long id, CarModelRequestDto carModelRequestDto) {
        return carModelRepository
                .findById(id)
                .map(existingEvent -> {
                    carModelMapper.partialUpdate(existingEvent, carModelRequestDto);
                    return existingEvent;
                })
                .map(carModelRepository::save)
                .map(carModelMapper::toDto)
                .orElseThrow(() -> new NoSuchEntityException(CarModel.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CarModel carModel = carModelRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(CarModel.class, id));
        carModelRepository.delete(carModel);
    }
}
