package ru.vsu.cs.zmaev.carservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.PartsInJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.IdResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.PartsInJobResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.entity.PartsInJob;
import ru.vsu.cs.zmaev.carservice.domain.mapper.PartsInJobMapper;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.carservice.repository.PartsInJobRepository;
import ru.vsu.cs.zmaev.carservice.service.PartsInJobService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartsInJobServiceImpl implements PartsInJobService {

    private final PartsInJobRepository partsInJobRepository;
    private final PartsInJobMapper partsInJobMapper;

    private final WebClient webClient;

    @Value("${car-parts.service.base-url}")
    private String carPartsServiceBaseUrl;

    @Override
    @Transactional(readOnly = true)
    public Page<PartsInJobResponseDto> findAll(Pageable pageable) {
        return partsInJobRepository.findAll(pageable).map(partsInJobMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartsInJobResponseDto> findAllByJobId(Pageable pageable, Long jobId) {
        return partsInJobRepository.findAllByCarJobJobType_Id(pageable, jobId).map(partsInJobMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PartsInJobResponseDto findOneById(Long id) {
        PartsInJob partsInJob = partsInJobRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException(PartsInJob.class, id));
        return partsInJobMapper.toDto(partsInJob);
    }

    @Override
    @Transactional
    public PartsInJobResponseDto update(Long id, PartsInJobRequestDto dto) {
        return null;
    }

    @Override
    @Transactional
    public PartsInJobResponseDto save(PartsInJobRequestDto dto) {
        IdResponseDto id = findPartByIdSync(dto.getCarPartId());
        log.info("ID:" + id);
        return null;
    }

    private IdResponseDto findPartByIdSync(final Long id) {
        return webClient
                .get()
                .uri(String.format("%s/api/car-parts/{id}", carPartsServiceBaseUrl), id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NoSuchEntityException(IdResponseDto.class, id));
                    } else {
                        return Mono.error(new RuntimeException("Client error"));
                    }
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(IdResponseDto.class)
                .block();
    }

    @Override
    @Transactional
    public void delete(Long id) {
    }
}
