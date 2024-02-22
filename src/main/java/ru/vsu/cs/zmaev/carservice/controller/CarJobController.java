package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarJobResponseDto;
import ru.vsu.cs.zmaev.carservice.service.CarJobService;

@RestController
@RequestMapping("api/car-job")
@RequiredArgsConstructor
public class CarJobController {

    private final CarJobService carJobService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<CarJobResponseDto>> findAll(
            @RequestParam Integer pagePosition,
            @RequestParam Integer pageSize
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carJobService.findAll(PageRequest.of(pagePosition, pageSize)));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarJobResponseDto> findOneById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(carJobService.findOneById(id));
    }

    @GetMapping(value = "/job/{jobId}", produces = "application/json")
    public ResponseEntity<Page<CarJobResponseDto>> findCarJobsByJobId(
            @PathVariable Long jobId,
            @RequestParam Integer pagePosition,
            @RequestParam Integer pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carJobService.findAllByJobId(PageRequest.of(pagePosition, pageSize), jobId));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<CarJobResponseDto> create(@Valid @RequestBody CarJobRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(carJobService.save(dto));
    }
}
