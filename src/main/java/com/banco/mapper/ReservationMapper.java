package com.banco.mapper;

import com.banco.dto.ReservationResponseDTO;
import com.banco.entity.Reservation;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static ReservationResponseDTO toDTO(Reservation reservation) {

        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .userId(reservation.getUser().getId())
                .spaceId(reservation.getSpace().getId())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .status(reservation.getStatus())
                .paymentStatus(reservation.getPaymentStatus())
                .totalPrice(reservation.getTotalPrice())
                .build();

    }

}