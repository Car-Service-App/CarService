package ru.vsu.cs.zmaev.carservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.JobTypeRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.JobTypeResponseDto;

@Tag(name = "Job Type Api", description = "API для работы с типами задач")
public interface JobTypeApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка типов задач",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JobTypeResponseDto.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение всех типов задач")
    ResponseEntity<Page<JobTypeResponseDto>> findAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат типа задачи по id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JobTypeResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Тип задачи по переданному id не существует"
            )
    })
    @Operation(summary = "Получение типа задачи по id")
    ResponseEntity<JobTypeResponseDto> findOneById(@Parameter(description = "id типа задачи") Long id);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное создание типа задачи",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JobTypeResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос"
            )
    })
    @Operation(summary = "Создание типа задачи")
    ResponseEntity<JobTypeResponseDto> create(@Valid JobTypeRequestDto dto);
}
