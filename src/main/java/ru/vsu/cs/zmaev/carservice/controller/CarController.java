package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.controller.api.CarApi;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.service.CarService;

import java.time.Instant;

@RestController
@RequestMapping("api/cars")
@RequiredArgsConstructor
public class CarController implements CarApi {

    private final CarService carService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<CarResponseDto>> findAllWithFilters(
            @RequestParam(defaultValue = "0") @Min(value = 0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(value = 1) Integer pageSize,
            @RequestParam(required = false) String carType,
            @RequestParam(required = false) Instant releaseYear,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String carModelName,
            @RequestParam(required = false) Long engineId,
            @RequestParam(required = false) String engineName,
            @RequestParam(required = false) String engineCapacity,
            @RequestParam(required = false) Long transmissionId,
            @RequestParam(required = false) String transmissionName,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        EntityPage entityPage =
                new EntityPage(pagePosition, pageSize, sortDirection, sortBy);
        CarCriteriaSearch criteriaSearch =
                new CarCriteriaSearch(
                        0L,
                        carType,
                        releaseYear,
                        brandName,
                        carModelName,
                        engineId,
                        engineName,
                        engineCapacity,
                        transmissionId,
                        transmissionName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService.findAllWithFilters(entityPage, criteriaSearch));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarResponseDto> findOneById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findOneById(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<CarResponseDto> create(@Valid @RequestBody CarRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.save(requestDto));
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarResponseDto> update(@PathVariable Long id, @Valid @RequestBody CarRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
