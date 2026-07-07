package com.banco.service;

import java.util.List;

import com.banco.dto.CreateReservationRequestDTO;
import com.banco.dto.ReservationResponseDTO;

public interface ReservationService {

    ReservationResponseDTO create(Long userId, CreateReservationRequestDTO dto);

    ReservationResponseDTO findById(Long id);

    List<ReservationResponseDTO> findAll();

    List<ReservationResponseDTO> findByUser(Long userId);

    void cancel(Long id);
}