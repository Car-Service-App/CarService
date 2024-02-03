package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.controller.api.ManufacturerApi;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.ManufacturerCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.ManufacturerRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.ManufacturerResponseDto;
import ru.vsu.cs.zmaev.carservice.service.ManufacturerService;

@RestController
@RequestMapping("api/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController implements ManufacturerApi {

    private final ManufacturerService manufacturerService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<ManufacturerResponseDto>> findAllWithFilters(
            @RequestParam(defaultValue = "0") @Min(value = 0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(value = 1) Integer pageSize,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String productionType,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        EntityPage entityPage =
                new EntityPage(pagePosition, pageSize, sortDirection, sortBy);
        ManufacturerCriteriaSearch criteriaSearch =
                new ManufacturerCriteriaSearch(0L, brandName, country, productionType);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(manufacturerService.findAllWithFilters(entityPage, criteriaSearch));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ManufacturerResponseDto> findOneById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(manufacturerService.findOneById(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<ManufacturerResponseDto> create(@Valid @RequestBody ManufacturerRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(manufacturerService.save(requestDto));
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ManufacturerResponseDto> update(@PathVariable Long id, @Valid @RequestBody ManufacturerRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(manufacturerService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        manufacturerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
