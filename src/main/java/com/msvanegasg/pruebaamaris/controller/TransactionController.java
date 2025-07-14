package com.msvanegasg.pruebaamaris.controller;

import com.msvanegasg.pruebaamaris.dto.TransactionRequestDTO;
import com.msvanegasg.pruebaamaris.dto.TransactionResponseDTO;
import com.msvanegasg.pruebaamaris.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> registrar(@Valid @RequestBody TransactionRequestDTO dto) {
        return ResponseEntity.ok(service.registrar(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TransactionResponseDTO>> historial(@PathVariable String userId) {
        return ResponseEntity.ok(service.obtenerHistorial(userId));
    }
}
