package com.banco.dto;

import java.time.LocalDateTime;

import com.banco.validation.ValidReservationDates;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidReservationDates
public class CreateReservationRequestDTO {

    @NotNull
    private Long spaceId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
}