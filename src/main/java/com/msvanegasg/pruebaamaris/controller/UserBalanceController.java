package com.msvanegasg.pruebaamaris.controller;

import com.msvanegasg.pruebaamaris.dto.UserBalanceRequestDTO;
import com.msvanegasg.pruebaamaris.dto.UserBalanceResponseDTO;
import com.msvanegasg.pruebaamaris.service.UserBalanceService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user-balance")
@Tag(name = "User Balance", description = "Operaciones sobre la vinculación de fondos del usuario")
public class UserBalanceController {

    private final UserBalanceService service;

    public UserBalanceController(UserBalanceService service) {
        this.service = service;
    }

    @PostMapping("/suscribir")
    @Operation(summary = "Suscribirse a un fondo", description = "Realiza la vinculación del usuario a un fondo, descontando el monto.")
    public ResponseEntity<UserBalanceResponseDTO> suscribir(@Valid @RequestBody UserBalanceRequestDTO dto) {
        return ResponseEntity.ok(service.suscribirAFondo(dto));
    }

    @PostMapping("/cancelar")
    @Operation(summary = "Cancelar suscripción a un fondo", description = "Realiza la cancelación de una suscripción, devolviendo el monto al usuario.")
    public ResponseEntity<UserBalanceResponseDTO> cancelar(@Valid @RequestBody UserBalanceRequestDTO dto) {
        return ResponseEntity.ok(service.cancelarFondo(dto));
    }

    @GetMapping("/historial/{userId}")
    @Operation(summary = "Historial de transacciones", description = "Obtiene el historial de movimientos (aperturas y cancelaciones) para un usuario.")
    public ResponseEntity<List<UserBalanceResponseDTO>> historial(@PathVariable String userId) {
        return ResponseEntity.ok(service.obtenerHistorial(userId));
    }
}
