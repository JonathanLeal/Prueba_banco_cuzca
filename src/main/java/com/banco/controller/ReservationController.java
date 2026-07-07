package com.banco.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.dto.CreateReservationRequestDTO;
import com.banco.dto.ReservationResponseDTO;
import com.banco.entity.User;
import com.banco.service.ReservationService;
import com.banco.serviceImplement.ReservationServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservations") 
@Tag(name = "2. Reservas", description = "Operaciones para agendar, listar y cancelar reservas de espacios")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationServiceImpl reservationServiceImpl;

    public ReservationController(ReservationService reservationService, ReservationServiceImpl reservationServiceImpl) {
        this.reservationService = reservationService;
        this.reservationServiceImpl = reservationServiceImpl;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reserva", description = "Valida la disponibilidad, analiza rangos de tiempo lógicos, simula el cobro vía Circuit Breaker y dispara notificaciones asíncronas.")
    public ResponseEntity<ReservationResponseDTO> create(
            @Valid @RequestBody CreateReservationRequestDTO dto,
            @AuthenticationPrincipal User user) {
        
        
        ReservationResponseDTO response = reservationService.create(user.getId(), dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    
    @GetMapping
    @Operation(summary = "Listar reservas", description = "Si el solicitante es ADMIN ve todo el histórico global. Si es USER, solo ve sus reservas personales.")
    public ResponseEntity<List<ReservationResponseDTO>> getAll(@AuthenticationPrincipal User user) {
        if ("ADMIN".equals(user.getRole().name())) { 
            return ResponseEntity.ok(reservationService.findAll());
        }
        return ResponseEntity.ok(reservationService.findByUser(user.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID", description = "Permite inspeccionar los detalles de una reserva en específico verificando pertenencia de datos.")
    public ResponseEntity<ReservationResponseDTO> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        
        ReservationResponseDTO dto = reservationService.findById(id);
        
        if (!"ADMIN".equals(user.getRole().name()) && !dto.getUserId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar reserva de forma segura", description = "Realiza la cancelación lógica de un espacio liberando los bloques horarios.")
    public ResponseEntity<Void> cancel(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        reservationServiceImpl.cancelSecure(id, user.getId(), user.getRole().name());
        return ResponseEntity.noContent().build();
    }
}