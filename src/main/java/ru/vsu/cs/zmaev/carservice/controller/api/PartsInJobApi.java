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
import ru.vsu.cs.zmaev.carservice.domain.dto.request.PartsInJobRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.PartsInJobResponseDto;

@Tag(name = "Parts in Job Api", description = "API для работы с деталями в задачах")
public interface PartsInJobApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка деталей в задачах",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PartsInJobResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение всех деталей в задачах")
    ResponseEntity<Page<PartsInJobResponseDto>> findAll(
            @Parameter(description = "Номер страницы") Integer pagePosition,
            @Parameter(description = "Размер страницы") Integer pageSize
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка деталей в задачах по id задачи",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PartsInJobResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение всех деталей в задачах по id задачи")
    ResponseEntity<Page<PartsInJobResponseDto>> findAllByJobId(
            @Parameter(description = "Номер страницы") Integer pagePosition,
            @Parameter(description = "Размер страницы") Integer pageSize,
            @Parameter(description = "id задачи") Long jobId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат детали в задаче по id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PartsInJobResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Деталь в задаче по переданному id не существует"
            )
    })
    @Operation(summary = "Получение детали в задаче по id")
    ResponseEntity<PartsInJobResponseDto> findOneById(@Parameter(description = "id детали в задаче") Long id);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное создание детали в задаче",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PartsInJobResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос"
            )
    })
    @Operation(summary = "Создание детали в задаче")
    ResponseEntity<PartsInJobResponseDto> create(@Valid PartsInJobRequestDto dto);
}