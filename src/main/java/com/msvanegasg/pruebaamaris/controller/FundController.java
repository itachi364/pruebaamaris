package com.msvanegasg.pruebaamaris.controller;

import com.msvanegasg.pruebaamaris.dto.FundRequestDTO;
import com.msvanegasg.pruebaamaris.dto.FundResponseDTO;
import com.msvanegasg.pruebaamaris.service.FundService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funds")
public class FundController {

	private final FundService service;

	public FundController(FundService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<FundResponseDTO> create(@Valid @RequestBody FundRequestDTO dto) {
		return ResponseEntity.ok(service.create(dto));
	}

	@GetMapping
	public ResponseEntity<List<FundResponseDTO>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<FundResponseDTO> getById(@PathVariable String id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<FundResponseDTO> update(@PathVariable String id, @RequestBody @Valid FundRequestDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

}
