package ru.vsu.cs.zmaev.carservice.domain.dto.response;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AllCarJobResponseDto {
    private Long carConfigId;
    private Long jobTypeId;
    private String jobTypeName;
    private Boolean isExist;
}

