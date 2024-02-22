package ru.vsu.cs.zmaev.carservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.JobTypeRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.JobTypeResponseDto;

public interface JobTypeService {
    Page<JobTypeResponseDto> findAll(Pageable pageable);

    JobTypeResponseDto findOneById(Long id);

    JobTypeResponseDto save(JobTypeRequestDto dto);

    JobTypeResponseDto update(Long id, JobTypeRequestDto dto);

    void delete(Long id);
}
