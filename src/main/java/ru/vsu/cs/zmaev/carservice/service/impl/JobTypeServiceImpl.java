package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.JobTypeRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.JobTypeResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.JobType;
import ru.vsu.cs.zmaev.carservice.domain.mapper.JobTypeMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.JobTypeRepository;
import ru.vsu.cs.zmaev.carservice.service.JobTypeService;

@Service
@RequiredArgsConstructor
public class JobTypeServiceImpl implements JobTypeService {

    private final JobTypeRepository jobTypeRepository;
    private final JobTypeMapper jobTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<JobTypeResponseDto> findAll(Pageable pageable) {
        return jobTypeRepository.findAll(pageable).map(jobTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public JobTypeResponseDto findOneById(Long id) {
        JobType jobType = jobTypeRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(JobType.class, id));
        return jobTypeMapper.toDto(jobType);
    }

    @Override
    @Transactional
    public JobTypeResponseDto save(JobTypeRequestDto dto) {
        JobType jobType = jobTypeMapper.toEntity(dto);
        jobTypeRepository.save(jobType);
        return jobTypeMapper.toDto(jobType);
    }

    @Override
    @Transactional
    public JobTypeResponseDto update(Long id, JobTypeRequestDto dto) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}
