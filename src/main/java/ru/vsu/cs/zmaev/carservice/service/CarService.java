package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarWithConfigResponseDto;

import java.util.List;

public interface CarService {
    Page<CarResponseDto> findAllWithFilters(EntityPage entityPage, CarCriteriaSearch carCriteriaSearch);
    Page<CarResponseDto> findAll(Pageable pageable);
    CarResponseDto findOneById(Long id);
    CarWithConfigResponseDto findCarWithConfigsById(Long id);
    List<CarResponseDto> findCarsByModelId(Long modelId);
    CarResponseDto save(CarRequestDto carRequestDto);
    CarResponseDto update(Long id, CarRequestDto carRequestDto);
    void delete(Long id);
}
