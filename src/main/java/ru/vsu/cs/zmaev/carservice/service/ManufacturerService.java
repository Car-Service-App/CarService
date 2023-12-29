package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.ManufacturerCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.ManufacturerRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.ManufacturerResponseDto;

public interface ManufacturerService {
    Page<ManufacturerResponseDto> findAllWithFilters(EntityPage entityPage, ManufacturerCriteriaSearch manufacturerCriteriaSearch);

    ManufacturerResponseDto findOneById(Long id);

    ManufacturerResponseDto update(Long id, ManufacturerRequestDto manufacturerRequestDto);

    ManufacturerResponseDto save(ManufacturerRequestDto manufacturerRequestDto);

    void delete(Long id);
}
