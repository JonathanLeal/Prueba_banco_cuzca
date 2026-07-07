package com.banco.dto;

import com.banco.enums.SpaceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSpaceRequestDTO {

    @NotBlank(message = "El nombre del espacio es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    private String name;

    @NotNull(message = "El tipo de espacio es obligatorio (MEETING_ROOM, DESK, PRIVATE_OFFICE)")
    private SpaceType type;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser un número positivo mayor a cero")
    private Integer capacity;

    @NotBlank(message = "La ubicación del espacio es obligatoria")
    @Size(max = 150, message = "La ubicación no puede superar los 150 caracteres")
    private String location;

    @NotNull(message = "La tarifa por hora es obligatoria")
    @Positive(message = "La tarifa por hora debe ser un valor positivo")
    private BigDecimal hourlyRate;
}