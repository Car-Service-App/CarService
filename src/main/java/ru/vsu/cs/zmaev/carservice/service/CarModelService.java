package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarModelCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarModelRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarModelResponseDto;

import java.util.List;

public interface CarModelService {
    Page<CarModelResponseDto> findAllWithFilters(EntityPage entityPage, CarModelCriteriaSearch carModelCriteriaSearch);

    List<CarModelResponseDto> findAllModelsByManufacturerId(Long manufacturerId);

    CarModelResponseDto findOneById(Long id);

    CarModelResponseDto save(CarModelRequestDto carModelRequestDto);

    CarModelResponseDto update(Long id, CarModelRequestDto carModelRequestDto);

    void delete(Long id);

    Page<CarModelResponseDto> findAll(Pageable pageable);

}
