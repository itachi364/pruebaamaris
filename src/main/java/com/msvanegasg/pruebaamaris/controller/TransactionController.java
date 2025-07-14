package com.msvanegasg.pruebaamaris.controller;

import com.msvanegasg.pruebaamaris.dto.TransactionRequestDTO;
import com.msvanegasg.pruebaamaris.dto.TransactionResponseDTO;
import com.msvanegasg.pruebaamaris.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transacciones", description = "Operaciones para registrar y consultar transacciones del usuario en fondos")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
        summary = "Registrar transacción",
        description = "Registra una nueva transacción (apertura o cancelación) del usuario sobre un fondo. "
                    + "Genera identificador único, actualiza saldo y envía notificación."
    )
    public ResponseEntity<TransactionResponseDTO> registrar(@Valid @RequestBody TransactionRequestDTO dto) {
        return ResponseEntity.ok(service.registrar(dto));
    }

    @GetMapping("/historial/{userId}")
    @Operation(
        summary = "Obtener historial de transacciones",
        description = "Devuelve el historial de transacciones (aperturas y cancelaciones) realizadas por el usuario."
    )
    public ResponseEntity<List<TransactionResponseDTO>> historial(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(service.obtenerHistorial(userId));
    }
}
