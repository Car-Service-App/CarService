package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.AllCarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.ExistingCarJobResponseDto;

import java.util.List;

public interface CarJobService {
    @Transactional(readOnly = true)
    Page<ExistingCarJobResponseDto> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    Page<ExistingCarJobResponseDto> findExistingByCarConfig(Pageable pageable, Long carConfigId);

    @Transactional(readOnly = true)
    List<AllCarJobResponseDto> findAllByCarConfig(Long carConfigId);


    @Transactional(readOnly = true)
    Page<ExistingCarJobResponseDto> findAllByJobId(Pageable pageable, Long jobId);

    @Transactional(readOnly = true)
    ExistingCarJobResponseDto findOneById(Long id);

    @Transactional
    ExistingCarJobResponseDto save(CarJobRequestDto dto);

    @Transactional
    ExistingCarJobResponseDto update(Long id, CarJobRequestDto dto);

    @Transactional
    void delete(Long id);
}
