package ru.vsu.cs.zmaev.carservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.carservice.controller.api.EngineApi;
import ru.vsu.cs.zmaev.carservice.domain.dto.EntityPage;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.EngineCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.EngineRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.EngineResponseDto;
import ru.vsu.cs.zmaev.carservice.service.EngineService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/engine")
public class EngineController implements EngineApi {

    private final EngineService engineService;

    @GetMapping
    public ResponseEntity<Page<EngineResponseDto>> findAllWithFilters(
            @RequestParam(defaultValue = "0") @Min(value = 0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(value = 1) Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String capacity,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        EntityPage entityPage = new EntityPage(pagePosition, pageSize, sortDirection, sortBy);
        EngineCriteriaSearch engineCriteriaSearch = new EngineCriteriaSearch(0L, name, capacity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(engineService.findAllWithFilters(entityPage, engineCriteriaSearch));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EngineResponseDto> findOneById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.findOneById(id));
    }

    @PostMapping()
    public ResponseEntity<EngineResponseDto> create(@Valid @RequestBody EngineRequestDto engineRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.save(engineRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EngineResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody EngineRequestDto engineRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.update(id, engineRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        engineService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
