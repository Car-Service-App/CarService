package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.controller.api.CarModelApi;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarModelCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarModelFilterRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarModelRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarModelResponseDto;
import ru.vsu.cs.zmaev.carservice.service.CarModelService;

import java.util.List;

@RestController
@RequestMapping("api/car-models")
@RequiredArgsConstructor
public class CarModelController implements CarModelApi {

    private final CarModelService carModelService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<CarModelResponseDto>> findAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pagePosition, pageSize);
        return ResponseEntity.ok().body(carModelService.findAll(pageable));
    }

    @PostMapping(value = "/filters", produces = "application/json")
    public ResponseEntity<Page<CarModelResponseDto>> findAllWithFilters(
            @Valid @RequestBody CarModelFilterRequestDto filterRequestDto,
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        EntityPage entityPage = new EntityPage(pagePosition, pageSize, sortDirection, sortBy);
        CarModelCriteriaSearch criteriaSearch = new CarModelCriteriaSearch(
                0L, filterRequestDto.getModelName(), filterRequestDto.getManufacturerName());
        return ResponseEntity.ok().body(carModelService.findAllWithFilters(entityPage, criteriaSearch));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarModelResponseDto> findOneById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(carModelService.findOneById(id));
    }

    @GetMapping(value = "manufacturer/{id}", produces = "application/json")
    public ResponseEntity<List<CarModelResponseDto>> findAllByManufacturerId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(carModelService.findAllModelsByManufacturerId(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<CarModelResponseDto> create(@Valid @RequestBody CarModelRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carModelService.save(requestDto));
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarModelResponseDto> update(@PathVariable Long id, @Valid @RequestBody CarModelRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(carModelService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carModelService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
