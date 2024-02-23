package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.JobTypeResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.Car;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarEngine;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarJob;
import ru.vsu.cs.zmaev.carservice.domain.entity.JobType;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarJobMapper;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarMapper;
import ru.vsu.cs.zmaev.carservice.domain.mapper.JobTypeMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.CarEngineRepository;
import ru.vsu.cs.zmaev.carservice.repository.CarJobRepository;
import ru.vsu.cs.zmaev.carservice.repository.CarRepository;
import ru.vsu.cs.zmaev.carservice.repository.JobTypeRepository;
import ru.vsu.cs.zmaev.carservice.service.CarJobService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarJobServiceImpl implements CarJobService {

    private final CarJobMapper carJobMapper;
    private final CarJobRepository carJobRepository;

    private final JobTypeRepository jobTypeRepository;
    private final JobTypeMapper jobTypeMapper;

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    private final CarEngineRepository carEngineRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CarJobResponseDto> findAll(Pageable pageable) {
        Page<CarJob> carJobsPage = carJobRepository.findAll(pageable);
        List<CarJobResponseDto> carJobResponseDtoList = carJobsPage.getContent().stream()
                .map(this::mapToDto).toList();
        return new PageImpl<>(carJobResponseDtoList, pageable, carJobsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarJobResponseDto> findAllByJobId(Pageable pageable, Long jobId) {
        Page<CarJob> carJobsPage = carJobRepository.findAllByJobType_Id(pageable, jobId);
        List<CarJobResponseDto> carJobResponseDtoList = carJobsPage.getContent().stream()
                .map(this::mapToDto).toList();
        return new PageImpl<>(carJobResponseDtoList, pageable, carJobsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public CarJobResponseDto findOneById(Long id) {
        CarJob carJob = carJobRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(CarJob.class, id));
        return mapToDto(carJob);
    }

    @Override
    @Transactional
    public CarJobResponseDto save(CarJobRequestDto dto) {
        CarJob carJob = carJobMapper.toEntity(dto);
        CarEngine carEngine = carEngineRepository.findById(dto.getCarEngineId()).orElseThrow(() ->
                new NoSuchEntityException(CarEngine.class, dto.getCarEngineId()));
        JobType jobType = jobTypeRepository.findById(dto.getJobTypeId()).orElseThrow(() ->
                new NoSuchEntityException(JobType.class, dto.getJobTypeId()));
        carJob.setCarEngine(carEngine);
        carJob.setJobType(jobType);
        carJobRepository.save(carJob);
        return carJobMapper.toDto(carJob);
    }

    @Override
    @Transactional
    public CarJobResponseDto update(Long id, CarJobRequestDto dto) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
    }

    private CarJobResponseDto mapToDto(CarJob carJob) {
        JobTypeResponseDto jobTypeResponseDto = jobTypeMapper.toDto(
                jobTypeRepository.findById(carJob.getJobType().getId()).orElseThrow(() ->
                        new NoSuchEntityException(JobType.class, carJob.getJobType().getId())));
        CarResponseDto carResponseDto = carMapper.toDto(
                carRepository.findById(carJob.getCarEngine().getCarId()).orElseThrow(() ->
                        new NoSuchEntityException(Car.class, carJob.getCarEngine().getCarId())));
        return new CarJobResponseDto(carJob.getId(), carJob.getCarEngine().getEngineId(), jobTypeResponseDto, carResponseDto);
    }
}
