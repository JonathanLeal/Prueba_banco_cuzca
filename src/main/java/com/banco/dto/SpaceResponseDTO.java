package com.banco.dto;

import com.banco.enums.SpaceType;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpaceResponseDTO {
    private Long id;
    private String name;
    private SpaceType type;
    private Integer capacity;
    private String location;
    private BigDecimal hourlyRate;
    private Boolean active;
}