package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;

public interface CarService {
    Page<CarResponseDto> findAllWithFilters(EntityPage entityPage, CarCriteriaSearch carCriteriaSearch);
    CarResponseDto findOneById(Long id);
    CarResponseDto save(CarRequestDto carRequestDto);
    CarResponseDto update(Long id, CarRequestDto carRequestDto);
    void delete(Long id);
}
