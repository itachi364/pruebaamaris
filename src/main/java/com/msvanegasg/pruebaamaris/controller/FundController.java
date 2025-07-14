package com.msvanegasg.pruebaamaris.controller;

import com.msvanegasg.pruebaamaris.dto.FundRequestDTO;
import com.msvanegasg.pruebaamaris.dto.FundResponseDTO;
import com.msvanegasg.pruebaamaris.service.FundService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/funds")
@Tag(name = "Funds", description = "Fondos")
public class FundController {

    private final FundService service;

    public FundController(FundService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear nuevo fondo")
    public ResponseEntity<FundResponseDTO> create(@Valid @RequestBody FundRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos los fondos")
    public ResponseEntity<List<FundResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener fondo por ID")
    public ResponseEntity<FundResponseDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar fondo por ID")
    public ResponseEntity<FundResponseDTO> update(@PathVariable("id") String id, @Valid @RequestBody FundRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar fondo por ID")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

