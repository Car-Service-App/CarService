package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.controller.api.CarJobApi;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.AllCarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.ExistingCarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.service.CarJobService;

import java.util.List;

@RestController
@RequestMapping("api/car-job")
public class CarJobController implements CarJobApi {

    private final CarJobService carJobService;

    public CarJobController(CarJobService carJobService) {
        this.carJobService = carJobService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<ExistingCarJobResponseDto>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Page<ExistingCarJobResponseDto> carJobs = carJobService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok().body(carJobs);
    }

    @GetMapping(value = "/car-config/{carConfigId}",produces = "application/json")
    public ResponseEntity<Page<ExistingCarJobResponseDto>> findAllByCarConfig(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @PathVariable Long carConfigId
    ) {
        Page<ExistingCarJobResponseDto> carJobs = carJobService
                .findExistingByCarConfig(PageRequest.of(page, size), carConfigId);
        return ResponseEntity.ok().body(carJobs);
    }

    @GetMapping(value = "/car-config/{id}/all",produces = "application/json")
    public ResponseEntity<List<AllCarJobResponseDto>> findAllByCarConfig(@PathVariable Long id) {
        List<AllCarJobResponseDto> carJobs = carJobService.findAllByCarConfig(id);
        return ResponseEntity.ok().body(carJobs);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ExistingCarJobResponseDto> findOneById(@PathVariable Long id) {
        ExistingCarJobResponseDto carJob = carJobService.findOneById(id);
        return ResponseEntity.ok().body(carJob);
    }

    @GetMapping(value = "/job/{jobId}", produces = "application/json")
    public ResponseEntity<Page<ExistingCarJobResponseDto>> findCarJobsByJobId(
            @PathVariable Long jobId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<ExistingCarJobResponseDto> carJobs = carJobService.findAllByJobId(PageRequest.of(page, size), jobId);
        return ResponseEntity.ok().body(carJobs);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<ExistingCarJobResponseDto> create(@Valid @RequestBody CarJobRequestDto dto) {
        ExistingCarJobResponseDto createdCarJob = carJobService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCarJob);
    }
}
