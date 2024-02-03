package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.EngineCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.EngineRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.EngineResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.Engine;
import ru.vsu.cs.zmaev.carservice.domain.mapper.EngineMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.EngineRepository;
import ru.vsu.cs.zmaev.carservice.repository.criteria.EngineCriteriaRepository;
import ru.vsu.cs.zmaev.carservice.service.EngineService;

@Service
@RequiredArgsConstructor
public class EngineServiceImpl implements EngineService {

    private final EngineRepository engineRepository;
    private final EngineCriteriaRepository engineCriteriaRepository;
    private final EngineMapper engineMapper;


    @Override
    @Transactional(readOnly = true)
    public Page<EngineResponseDto> findAllWithFilters(EntityPage entityPage, EngineCriteriaSearch engineCriteriaSearch) {
        return engineCriteriaRepository.findAllWithFilters(entityPage, engineCriteriaSearch).map(engineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public EngineResponseDto findOneById(Long id) {
        Engine engine = engineRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(Engine.class, id));
        return engineMapper.toDto(engine);
    }

    @Override
    @Transactional
    public EngineResponseDto save(EngineRequestDto engineRequestDto) {
        Engine engine = engineRepository.save(engineMapper.toEntity(engineRequestDto));
        return engineMapper.toDto(engine);
    }

    @Override
    @Transactional
    public EngineResponseDto update(Long id, EngineRequestDto engineRequestDto) {
        return engineRepository
                .findById(id)
                .map(existingEvent -> {
                    engineMapper.partialUpdate(existingEvent, engineRequestDto);
                    return existingEvent;
                })
                .map(engineRepository::save)
                .map(engineMapper::toDto)
                .orElseThrow(() -> new NoSuchEntityException(Engine.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Engine engine = engineRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(Engine.class, id));
        engineRepository.delete(engine);
    }
}
