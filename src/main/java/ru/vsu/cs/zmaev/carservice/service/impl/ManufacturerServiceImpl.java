package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.ManufacturerCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.ManufacturerRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.ManufacturerResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.Manufacturer;
import ru.vsu.cs.zmaev.carservice.domain.mapper.ManufacturerMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.ManufacturerRepository;
import ru.vsu.cs.zmaev.carservice.repository.criteria.ManufacturerCriteriaRepository;
import ru.vsu.cs.zmaev.carservice.service.ManufacturerService;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerCriteriaRepository manufacturerCriteriaRepository;

    private final ManufacturerMapper manufacturerMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ManufacturerResponseDto> findAllWithFilters(EntityPage entityPage, ManufacturerCriteriaSearch criteriaSearch) {
        return manufacturerCriteriaRepository
                .findAllWithFilters(entityPage, criteriaSearch)
                .map(manufacturerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ManufacturerResponseDto findOneById(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(Manufacturer.class, id));
        return manufacturerMapper.toDto(manufacturer);
    }

    @Override
    @Transactional
    public ManufacturerResponseDto save(ManufacturerRequestDto requestDto) {
        Manufacturer manufacturer = manufacturerRepository.save(manufacturerMapper.toEntity(requestDto));
        return manufacturerMapper.toDto(manufacturer);
    }

    @Override
    @Transactional
    public ManufacturerResponseDto update(Long id, ManufacturerRequestDto dto) {
        return manufacturerRepository
                .findById(id)
                .map(existingEvent -> {
                    manufacturerMapper.partialUpdate(existingEvent, dto);
                    return existingEvent;
                })
                .map(manufacturerRepository::save)
                .map(manufacturerMapper::toDto)
                .orElseThrow(() -> new NoSuchEntityException(Manufacturer.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(Manufacturer.class, id));
        manufacturerRepository.delete(manufacturer);
    }
}