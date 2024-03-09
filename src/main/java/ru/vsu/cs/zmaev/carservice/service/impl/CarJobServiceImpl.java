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
import ru.vsu.cs.zmaev.carservice.domain.entity.CarConfig;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarJob;
import ru.vsu.cs.zmaev.carservice.domain.entity.JobType;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarConfigMapper;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarJobMapper;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarMapper;
import ru.vsu.cs.zmaev.carservice.domain.mapper.JobTypeMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.CarConfigRepository;
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
    private final CarConfigRepository carConfigRepository;
    private final CarConfigMapper carConfigMapper;

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
    public Page<CarJobResponseDto> findAllByCarConfig(Pageable pageable, Long carConfigId) {
        CarConfig carConfig = carConfigRepository.findById(carConfigId).orElseThrow(() ->
                new NoSuchEntityException(CarConfig.class, carConfigId));
        Page<CarJob> carJobsPage = carJobRepository.findAllByCarConfig(pageable, carConfig);
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
        CarConfig carConfig = carConfigRepository.findById(dto.getCarConfigId()).orElseThrow(() ->
                new NoSuchEntityException(CarConfig.class, dto.getCarConfigId()));
        JobType jobType = jobTypeRepository.findById(dto.getJobTypeId()).orElseThrow(() ->
                new NoSuchEntityException(JobType.class, dto.getJobTypeId()));
        carJob.setCarConfig(carConfig);
        carJob.setJobType(jobType);
        carJobRepository.save(carJob);
        return carJobMapper.toDto(carJob);
    }

    @Override
    @Transactional
    public CarJobResponseDto update(Long id, CarJobRequestDto dto) {
        // Impl logic
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Impl logic
    }

    private CarJobResponseDto mapToDto(CarJob carJob) {
        JobTypeResponseDto jobTypeResponseDto = jobTypeMapper.toDto(
                jobTypeRepository.findById(carJob.getJobType().getId()).orElseThrow(() ->
                        new NoSuchEntityException(JobType.class, carJob.getJobType().getId())));
        CarResponseDto carResponseDto = carMapper.toDto(
                carRepository.findById(carJob.getCarConfig().getCar().getId()).orElseThrow(() ->
                        new NoSuchEntityException(Car.class, carJob.getCarConfig().getCar().getId())));
        return new CarJobResponseDto(
                carJob.getId(),
                carConfigMapper.toDto(carJob.getCarConfig()),
                jobTypeResponseDto,
                carResponseDto,
                carJob.getMileage(),
                carJob.getTime());
    }
}
