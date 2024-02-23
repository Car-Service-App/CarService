package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.PartsInJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.PartsInJobResponseDto;
import ru.vsu.cs.zmaev.carservice.service.PartsInJobService;

@RestController
@RequestMapping("/api/parts-in-job")
@RequiredArgsConstructor
public class PartsInJobController {

    private final PartsInJobService partsInJobService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<PartsInJobResponseDto>> findAll(
            @RequestParam Integer pagePosition,
            @RequestParam Integer pageSize
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(partsInJobService.findAll(PageRequest.of(pagePosition, pageSize)));
    }

    @GetMapping(value = "job/{jobId}", produces = "application/json")
    public ResponseEntity<Page<PartsInJobResponseDto>> findAllByJobId(
            @RequestParam Integer pagePosition,
            @RequestParam Integer pageSize,
            @PathVariable Long jobId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(partsInJobService.findAllByJobId(PageRequest.of(pagePosition, pageSize), jobId));
    }

    @GetMapping(value = "/id", produces = "application/json")
    public ResponseEntity<PartsInJobResponseDto> findOneById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(partsInJobService.findOneById(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<PartsInJobResponseDto> create(@Valid @RequestBody PartsInJobRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(partsInJobService.save(dto));
    }
}
