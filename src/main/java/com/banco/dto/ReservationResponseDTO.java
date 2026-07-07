package com.banco.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banco.enums.PaymentStatus;
import com.banco.enums.ReservationStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponseDTO {

    private Long id;

    private Long userId;

    private Long spaceId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private ReservationStatus status;

    private PaymentStatus paymentStatus;

    private BigDecimal totalPrice;
}