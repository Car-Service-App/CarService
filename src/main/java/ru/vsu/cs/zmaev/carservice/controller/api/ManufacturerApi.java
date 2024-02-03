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
import ru.vsu.cs.zmaev.carservice.domain.dto.request.ManufacturerRequestDto;
import ru.vsu.cs.zmaev.carservice.domain.dto.response.ManufacturerResponseDto;

@Tag(name = "Manufacturer Api", description = "Api для работы с производителями")
public interface ManufacturerApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат списка производителей",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ManufacturerResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный тип продукции",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение всех производителей")
    ResponseEntity<Page<ManufacturerResponseDto>> findAllWithFilters(
            @Parameter(description = "Начальная страница")
            @RequestParam(defaultValue = "0") @Min(value = 0)
            Integer pagePosition,
            @Parameter(description = "Размер страницы")
            @RequestParam(defaultValue = "10") @Min(value = 1)
            Integer pageSize,
            @RequestParam(required = false)
            @Parameter(description = "Название брэнда производителя") String brandName,
            @RequestParam(required = false)
            @Parameter(description = "Страна производителя") String country,
            @RequestParam(required = false)
            @Parameter(
                    description = "Тип продукции",
                    in = ParameterIn.QUERY,
                    schema = @Schema(allowableValues = {"ALL", "ONLY_CARS", "ONLY_PARTS"}))
            String productionType,
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
                    description = "Успешный возврат производителя",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ManufacturerResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Производителя по переданному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Получение производителя по id")
    ResponseEntity<ManufacturerResponseDto> findOneById(@Parameter(description = "id производителя") Long id);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный создание производителя",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ManufacturerResponseDto.class)
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
    @Operation(summary = "Создание производителя")
    ResponseEntity<ManufacturerResponseDto> create(@Valid ManufacturerRequestDto dto);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный обновление производителя",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ManufacturerResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Производителя по переданному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Обновление производителя по id")
    ResponseEntity<ManufacturerResponseDto> update(
            @Parameter(description = "id производителя")
            Long id,
            @Parameter(description = "Dto звпроса производителя")
            @Valid
            ManufacturerRequestDto dto);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное удаление производителя"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Производителя по переданному id не существует",
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
    @Operation(summary = "Удаление производителя по id")
    ResponseEntity<Void> delete(@Parameter(description = "id производителя") Long id);

}