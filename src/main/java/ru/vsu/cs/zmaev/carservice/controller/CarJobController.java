package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.controller.api.CarJobApi;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.service.CarJobService;

@RestController
@RequestMapping("api/car-job")
public class CarJobController implements CarJobApi {

    private final CarJobService carJobService;

    public CarJobController(CarJobService carJobService) {
        this.carJobService = carJobService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<CarJobResponseDto>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Page<CarJobResponseDto> carJobs = carJobService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok().body(carJobs);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarJobResponseDto> findOneById(@PathVariable Long id) {
        CarJobResponseDto carJob = carJobService.findOneById(id);
        return ResponseEntity.ok().body(carJob);
    }

    @GetMapping(value = "/job/{jobId}", produces = "application/json")
    public ResponseEntity<Page<CarJobResponseDto>> findCarJobsByJobId(
            @PathVariable Long jobId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<CarJobResponseDto> carJobs = carJobService.findAllByJobId(PageRequest.of(page, size), jobId);
        return ResponseEntity.ok().body(carJobs);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<CarJobResponseDto> create(@Valid @RequestBody CarJobRequestDto dto) {
        CarJobResponseDto createdCarJob = carJobService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCarJob);
    }
}
