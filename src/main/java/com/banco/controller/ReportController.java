package com.banco.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.dto.SpaceOccupancyReportDTO;
import com.banco.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "4. Reportes Gerenciales", description = "Endpoints analíticos y estadísticos de alta demanda gerencial")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/occupancy")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Reporte de Ocupación con Caché (Solo ADMIN)", description = "Calcula la tasa porcentual de uso de cada sala en un rango temporal. Emplea caché en memoria RAM para optimización de alto rendimiento.")
    public ResponseEntity<List<SpaceOccupancyReportDTO>> getOccupancyReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<SpaceOccupancyReportDTO> report = reportService.getOccupancyReport(start, end);
        return ResponseEntity.ok(report);
    }
}