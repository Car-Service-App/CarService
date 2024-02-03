package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.EngineCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.EngineRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.EngineResponseDto;

public interface EngineService {
    Page<EngineResponseDto> findAllWithFilters(EntityPage entityPage, EngineCriteriaSearch engineCriteriaSearch);

    EngineResponseDto findOneById(Long id);

    EngineResponseDto save(EngineRequestDto engineRequestDto);

    EngineResponseDto update(Long id, EngineRequestDto engineRequestDto);

    void delete(Long id);
}
