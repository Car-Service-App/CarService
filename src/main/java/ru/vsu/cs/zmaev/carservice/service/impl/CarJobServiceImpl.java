package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarPartsCreateCarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CreateCarPartInCarPartServiceRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.*;
import ru.vsu.cs.zmaev.carservice.domain.entity.*;
import ru.vsu.cs.zmaev.carservice.domain.mapper.*;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.*;
import ru.vsu.cs.zmaev.carservice.service.CarJobService;

import java.util.*;

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
    private final PartsInJobRepository partsInJobRepository;

    private final WebClient webClient;
    private final EngineRepository engineRepository;
    private final EngineMapperImpl engineMapperImpl;
    private final TransmissionRepository transmissionRepository;

    @Value("${car-parts.service.base-url}")
    private String carPartsServiceBaseUrl;

    @Override
    @Transactional(readOnly = true)
    public Page<ExistingCarJobResponseDto> findAll(Pageable pageable) {
        Page<CarJob> carJobsPage = carJobRepository.findAll(pageable);
        List<ExistingCarJobResponseDto> existingCarJobResponseDtoList = carJobsPage.getContent().stream()
                .map(this::mapToDto).toList();
        return new PageImpl<>(existingCarJobResponseDtoList, pageable, carJobsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExistingCarJobResponseDto> findExistingByCarConfig(Pageable pageable, Long carConfigId) {
        CarConfig carConfig = carConfigRepository.findById(carConfigId).orElseThrow(() ->
                new NoSuchEntityException(CarConfig.class, carConfigId));
        Page<CarJob> carJobsPage = carJobRepository.findAllByCarConfig(pageable, carConfig);
        List<ExistingCarJobResponseDto> existingCarJobResponseDtoList = carJobsPage.getContent().stream()
                .map(this::mapToDto).toList();
        return new PageImpl<>(existingCarJobResponseDtoList, pageable, carJobsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllCarJobResponseDto> findAllByCarConfig(Long carConfigId) {
        CarConfig carConfig = carConfigRepository.findById(carConfigId).orElseThrow(() ->
                new NoSuchEntityException(CarConfig.class, carConfigId));
        List<CarJob> carJobsByConfig = carJobRepository.findAllByCarConfig(carConfig);
        List<JobType> jobTypes = jobTypeRepository.findAll();
        Map<String, AllCarJobResponseDto> carJobMap = new HashMap<>();
        for (CarJob carJob : carJobsByConfig) {
            AllCarJobResponseDto carJobDto = carJobMapper.toResponse(carJob);
            carJobMap.put(carJobDto.getJobTypeName(), carJobDto);
        }
        List<AllCarJobResponseDto> allCarJobResponseDtos = new ArrayList<>();
        for (JobType jobType : jobTypes) {
            AllCarJobResponseDto carJobDto = carJobMap.get(jobType.getJobName());
            if (carJobDto != null) {
                carJobDto.setIsExist(true);
            } else {
                carJobDto = new AllCarJobResponseDto();
                carJobDto.setJobTypeName(jobType.getJobName());
                carJobDto.setJobTypeId(jobType.getId());
                carJobDto.setIsExist(false);
                carJobDto.setCarConfigId(carConfigId);
            }
            allCarJobResponseDtos.add(carJobDto);
        }
        return allCarJobResponseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExistingCarJobResponseDto> findAllByJobId(Pageable pageable, Long jobId) {
        Page<CarJob> carJobsPage = carJobRepository.findAllByJobType_Id(pageable, jobId);
        List<ExistingCarJobResponseDto> existingCarJobResponseDtoList = carJobsPage.getContent().stream()
                .map(this::mapToDto).toList();
        return new PageImpl<>(existingCarJobResponseDtoList, pageable, carJobsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public ExistingCarJobResponseDto findOneById(Long id) {
        CarJob carJob = carJobRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(CarJob.class, id));
        return mapToDto(carJob);
    }

    @Override
    @Transactional
    public ExistingCarJobResponseDto save(CarJobRequestDto dto) {
        CarConfig carConfig = carConfigRepository.findById(dto.getCarConfigId()).orElseThrow(() ->
                new NoSuchEntityException(CarConfig.class, dto.getCarConfigId()));
        JobType jobType = jobTypeRepository.findById(dto.getJobTypeId()).orElseThrow(() ->
                new NoSuchEntityException(JobType.class, dto.getJobTypeId()));

        // Попытка найти существующую задачу CarJob

        List<CarJob> existingCarJobs = carJobRepository.findByCarConfigIdAndJobTypeId(dto.getCarConfigId(), dto.getJobTypeId());
        CarJob carJob;
        if (existingCarJobs.isEmpty()) {
            carJob = new CarJob(); // Создаем новый, если не найдено
        } else {
            carJob = existingCarJobs.get(0); // Используем первый найденный, если есть
        }

        carJob.setCarConfig(carConfig);
        carJob.setJobType(jobType);
        carJob.setMileage(dto.getMileage());
        carJob.setTime(dto.getTime());
        // Обновляем или сохраняем CarJob
        carJob = carJobRepository.save(carJob);

        List<CarPartsCreateCarJobRequestDto> carPartsDtoList = dto.getCarParts();
        for (CarPartsCreateCarJobRequestDto part : carPartsDtoList) {
            Long carPartId = saveCarPart(new CreateCarPartInCarPartServiceRequestDto(
                    dto.getCarConfigId(),
                    part.getCarPartTypeId(),
                    part.getOem(),
                    "/sample-image" // TODO: Доделать штуку с картинками
            ));
            // Попытка найти существующую запись PartsInJob
            Optional<PartsInJob> existingPartsInJob = partsInJobRepository.findByCarPartIdAndCarJob(carPartId, carJob);
            PartsInJob partsInJob = existingPartsInJob.orElseGet(PartsInJob::new); // Если не найдена, создаем новую
            partsInJob.setCarPartId(carPartId);
            partsInJob.setCarJob(carJob);
            partsInJob.setAmount(part.getAmount());
            // Обновляем или сохраняем PartsInJob
            partsInJobRepository.save(partsInJob);
        }
        return carJobMapper.toDto(carJob);
    }

    @Override
    @Transactional
    public ExistingCarJobResponseDto update(Long id, CarJobRequestDto dto) {
        // Impl logic
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Impl logic
    }

    private ExistingCarJobResponseDto mapToDto(CarJob carJob) {
        JobTypeResponseDto jobTypeResponseDto = jobTypeMapper.toDto(
                jobTypeRepository.findById(carJob.getJobType().getId()).orElseThrow(() ->
                        new NoSuchEntityException(JobType.class, carJob.getJobType().getId())));
        CarResponseDto carResponseDto = carMapper.toDto(
                carRepository.findById(carJob.getCarConfig().getCar().getId()).orElseThrow(() ->
                        new NoSuchEntityException(Car.class, carJob.getCarConfig().getCar().getId())));
        ExistingCarJobResponseDto existingCarJobResponseDto = new ExistingCarJobResponseDto();
        existingCarJobResponseDto.setId(carJob.getId());
        existingCarJobResponseDto.setCarId(carResponseDto.getId());
        existingCarJobResponseDto.setCarConfigId(carJob.getCarConfig().getCar().getId());
        existingCarJobResponseDto.setJobName(jobTypeResponseDto.getJobName());
        existingCarJobResponseDto.setManufacturerName(carResponseDto.getManufacturerName());
        existingCarJobResponseDto.setCarModelName(carResponseDto.getModelName());
        Long engineId = carJob.getCarConfig().getEngineId();
        EngineResponseDto engineResponseDto = engineMapperImpl.toDto(engineRepository.findById(engineId).orElseThrow(
                () -> new NoSuchEntityException(Engine.class, engineId)));
        Long transmissionId = carJob.getCarConfig().getTransmissionId();
        String transmissionName = transmissionRepository.findById(transmissionId).orElseThrow(
                () -> new NoSuchEntityException(Transmission.class, transmissionId)
        ).getTransmissionType().name();
        existingCarJobResponseDto.setEngineId(engineId);
        existingCarJobResponseDto.setEngineName(engineResponseDto.getName());
        existingCarJobResponseDto.setEngineCapacity(engineResponseDto.getCapacity());
        existingCarJobResponseDto.setTransmissionId(transmissionId);
        existingCarJobResponseDto.setTransmissionName(transmissionName);
        existingCarJobResponseDto.setMileage(carJob.getMileage());
        existingCarJobResponseDto.setTime(carJob.getTime());
        return existingCarJobResponseDto;
    }

    private Long saveCarPart(final CreateCarPartInCarPartServiceRequestDto dto) {
        return webClient
                .post()
                .uri(String.format("%s/api/car-parts/car-service", carPartsServiceBaseUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NoSuchEntityException("As"));
                    } else {
                        return Mono.error(new RuntimeException("Client error"));
                    }
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(Long.class)
                .block();
    }
}
