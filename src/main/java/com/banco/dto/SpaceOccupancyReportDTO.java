package com.banco.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceOccupancyReportDTO {
    private Long spaceId;
    private String spaceName;
    private BigDecimal occupancyPercentage; // Ejemplo: 75.50%
}