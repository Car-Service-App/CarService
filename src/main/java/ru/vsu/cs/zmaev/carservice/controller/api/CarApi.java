package ru.vsu.cs.zmaev.carservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarFilterRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarResponseDto;

import java.util.List;

@Tag(name = "Car Api", description = "API для работы с автомобилями")
public interface CarApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка автомобилей",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение всех автомобилей")
    ResponseEntity<Page<CarResponseDto>> findAll(@RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
                                                 @RequestParam(defaultValue = "10") @Min(1) Integer pageSize);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка автомобилей с фильтрами",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение всех автомобилей с фильтрами")
    ResponseEntity<Page<CarResponseDto>> findAllWithFilters(
            @RequestBody @Valid CarFilterRequestDto filterRequestDto,
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат автомобиля",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение автомобиля по id")
    ResponseEntity<CarResponseDto> findOneById(@PathVariable Long id);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка автомобилей по id модели",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение списка автомобилей по id модели")
    ResponseEntity<List<CarResponseDto>> findCarByModelId(@PathVariable Long modelId);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешное создание автомобиля",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Создание автомобиля")
    ResponseEntity<CarResponseDto> create(@Valid @RequestBody CarRequestDto requestDto);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление автомобиля",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Обновление автомобиля по id")
    ResponseEntity<CarResponseDto> update(@PathVariable Long id, @Valid @RequestBody CarRequestDto dto);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное удаление автомобиля"
            )
    })
    @Operation(summary = "Удаление автомобиля по id")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
