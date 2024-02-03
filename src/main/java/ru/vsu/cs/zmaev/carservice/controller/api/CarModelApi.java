package ru.vsu.cs.zmaev.carservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.cs.zmaev.carservice.domain.dto.ErrorMessage;
import ru.vsu.cs.zmaev.carservice.domain.dto.request.CarModelRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.CarModelResponseDto;

@Tag(name = "CarModel Api", description = "Api для работы с моделями автомобилей")
public interface CarModelApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка моделей автомобиля",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarModelResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный тип модели автомобиля",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение всех моделей автомобиля")
    ResponseEntity<Page<CarModelResponseDto>> findAllWithFilters(
            @Parameter(description = "Начальная страница")
            @RequestParam(defaultValue = "0") @Min(value = 0)
            Integer pagePosition,
            @Parameter(description = "Размер страницы")
            @RequestParam(defaultValue = "10") @Min(value = 1)
            Integer pageSize,
            @RequestParam(required = false)
            @Parameter(description = "Название модели")
            String modelName,
            @RequestParam(required = false)
            @Parameter(description = "Название производителя")
            String manufacturerName,
            @RequestParam(required = false)
            @Parameter(description = "Поле для сортировки") String sortBy,
            @RequestParam(required = false)
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Порядок сортировки",
                    name = "sortDirection",
                    schema = @Schema(allowableValues = {
                            "ASC",
                            "DESC"
                    }))
            Sort.Direction sortDirection);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат модели автомобиля",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarModelResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Модели автомобиля по переданному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение модели автомобиля по id")
    ResponseEntity<CarModelResponseDto> findOneById(@Parameter(description = "id модели автомобиля") Long id);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный создание модели автомобиля",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarModelResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Создание модели автомобиля")
    ResponseEntity<CarModelResponseDto> create(@Valid CarModelRequestDto dto);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное создание модели автомобиля",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CarModelResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Модели автомобиля по переданному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Обновление модели автомобиля по id")
    ResponseEntity<CarModelResponseDto> update(
            @Parameter(description = "id модели автомобиля")
            Long id,
            @Parameter(description = "Dto запроса модели автомобиля")
            @Valid
            CarModelRequestDto dto);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное удаление модели автомобиля"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Модели автомобиля по переданному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Удаление модели автомобиля по id")
    ResponseEntity<Void> delete(@Parameter(description = "id автомобиля") Long id);
}
