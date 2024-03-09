package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.controller.api.JobTypeApi;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.JobTypeRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.JobTypeResponseDto;
import ru.vsu.cs.zmaev.carservice.service.JobTypeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/job-type")
public class JobTypeController implements JobTypeApi {

    private final JobTypeService jobTypeService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<JobTypeResponseDto>> findAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobTypeService.findAll(PageRequest.of(pagePosition, pageSize)));
    }

    @GetMapping(value = "/id", produces = "application/json")
    public ResponseEntity<JobTypeResponseDto> findOneById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobTypeService.findOneById(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<JobTypeResponseDto> create(@Valid @RequestBody JobTypeRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(jobTypeService.save(dto));
    }
}
