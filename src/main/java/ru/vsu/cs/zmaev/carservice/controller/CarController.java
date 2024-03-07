package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.controller.api.CarApi;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarFilterRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;
import ru.vsu.cs.zmaev.carservice.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController implements CarApi {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<CarResponseDto>> findAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize
    ) {
        Page<CarResponseDto> cars = carService.findAll(PageRequest.of(pagePosition, pageSize));
        return ResponseEntity.ok().body(cars);
    }

    @PostMapping(value = "/filters", produces = "application/json")
    public ResponseEntity<Page<CarResponseDto>> findAllWithFilters(
            @RequestBody @Valid CarFilterRequestDto filterRequestDto,
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        EntityPage entityPage = new EntityPage(pagePosition, pageSize, sortDirection, sortBy);
        CarCriteriaSearch criteriaSearch = new CarCriteriaSearch(
                0L,
                filterRequestDto.getCarType(),
                filterRequestDto.getReleaseYear(),
                filterRequestDto.getBrandName(),
                filterRequestDto.getCarModelName(),
                filterRequestDto.getEngineId(),
                filterRequestDto.getEngineName(),
                filterRequestDto.getEngineCapacity(),
                filterRequestDto.getTransmissionId(),
                filterRequestDto.getTransmissionName());
        return ResponseEntity.ok().body(carService.findAllWithFilters(entityPage, criteriaSearch));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarResponseDto> findOneById(@PathVariable Long id) {
        return ResponseEntity.ok().body(carService.findOneById(id));
    }

    @GetMapping(value = "/model/{modelId}", produces = "application/json")
    public ResponseEntity<List<CarResponseDto>> findCarByModelId(@PathVariable Long modelId) {
        return ResponseEntity.ok().body(carService.findCarsByModelId(modelId));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<CarResponseDto> create(@Valid @RequestBody CarRequestDto requestDto) {
        CarResponseDto responseDto = carService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarResponseDto> update(@PathVariable Long id, @Valid @RequestBody CarRequestDto dto) {
        CarResponseDto responseDto = carService.update(id, dto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
