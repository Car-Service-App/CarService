package ru.vsu.cs.zmaev.carservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarJobResponseDto;

@Tag(name = "Car Job Api", description = "API для работы с работами по автомобилям")

public interface CarJobApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка работ по автомобилям",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarJobResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение всех работ по автомобилям")
    ResponseEntity<Page<CarJobResponseDto>> findAll(
            @Parameter(description = "Номер страницы") Integer pagePosition,
            @Parameter(description = "Размер страницы") Integer pageSize
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат работы по автомобилю",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarJobResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Работа по переданному id не существует"
            )
    })
    @Operation(summary = "Получение работы по id")
    ResponseEntity<CarJobResponseDto> findOneById(@Parameter(description = "id работы") Long id);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка работ по автомобилю",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarJobResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение работ по id задачи")
    ResponseEntity<Page<CarJobResponseDto>> findCarJobsByJobId(
            @Parameter(description = "id задачи") Long jobId,
            @Parameter(description = "Номер страницы") Integer pagePosition,
            @Parameter(description = "Размер страницы") Integer pageSize
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное создание работы по автомобилю",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarJobResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос"
            )
    })
    @Operation(summary = "Создание работы по автомобилю")
    ResponseEntity<CarJobResponseDto> create(@Valid CarJobRequestDto dto);
}

