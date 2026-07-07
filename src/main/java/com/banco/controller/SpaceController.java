package com.banco.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.dto.CreateSpaceRequestDTO;
import com.banco.dto.SpaceResponseDTO;
import com.banco.service.SpaceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/spaces")
@RequiredArgsConstructor
@Tag(name = "3. Espacios", description = "Mantenimiento y catálogo de salas de coworking (Acceso total: ADMIN / Lectura: USER)")
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear espacio (Solo ADMIN)", description = "Da de alta una nueva sala de juntas, oficina o escritorio en el sistema.")
    public ResponseEntity<SpaceResponseDTO> createSpace(@Valid @RequestBody CreateSpaceRequestDTO dto) {
        SpaceResponseDTO response = spaceService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener espacio por ID", description = "Retorna la ficha de características e infraestructura de un espacio específico.")
    public ResponseEntity<SpaceResponseDTO> getSpaceById(@PathVariable Long id) {
        return ResponseEntity.ok(spaceService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar catálogo completo de espacios", description = "Muestra la grilla general de espacios disponibles para todo público.")
    public ResponseEntity<List<SpaceResponseDTO>> getAllSpaces() {
        return ResponseEntity.ok(spaceService.findAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar espacio (Solo ADMIN)", description = "Modifica tarifas, capacidades o locación del recurso físico.")
    public ResponseEntity<SpaceResponseDTO> updateSpace(
            @PathVariable Long id, 
            @Valid @RequestBody CreateSpaceRequestDTO dto) {
        return ResponseEntity.ok(spaceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar/Deshabilitar espacio (Solo ADMIN)", description = "Aplica un borrado lógico para suspender el espacio evitando que reciba nuevas reservas.")
    public ResponseEntity<?> deleteSpace(@PathVariable Long id) {
        spaceService.delete(id);
        return ResponseEntity.ok(Map.of(
            "message", "El espacio ha sido deshabilitado/eliminado lógicamente de manera exitosa"
        ));
    }
}