package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.PartsInJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.PartsInJobResponseDto;

public interface PartsInJobService {

    @Transactional(readOnly = true)
    Page<PartsInJobResponseDto> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    Page<PartsInJobResponseDto> findAllByJobId(Pageable pageable, Long jobId);

    @Transactional(readOnly = true)
    PartsInJobResponseDto findOneById(Long id);

    @Transactional
    PartsInJobResponseDto update(Long id, PartsInJobRequestDto dto);

    @Transactional
    PartsInJobResponseDto save(PartsInJobRequestDto dto);

    @Transactional
    void delete(Long id);
}
