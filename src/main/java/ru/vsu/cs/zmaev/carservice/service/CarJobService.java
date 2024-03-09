package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarJobResponseDto;

public interface CarJobService {
    @Transactional(readOnly = true)
    Page<CarJobResponseDto> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    Page<CarJobResponseDto> findAllByCarConfig(Pageable pageable, Long carConfigId);

    @Transactional(readOnly = true)
    Page<CarJobResponseDto> findAllByJobId(Pageable pageable, Long jobId);

    @Transactional(readOnly = true)
    CarJobResponseDto findOneById(Long id);

    @Transactional
    CarJobResponseDto save(CarJobRequestDto dto);

    @Transactional
    CarJobResponseDto update(Long id, CarJobRequestDto dto);

    @Transactional
    void delete(Long id);
}
