package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.FundRequestDTO;
import com.msvanegasg.pruebaamaris.dto.FundResponseDTO;
import com.msvanegasg.pruebaamaris.mapper.FundMapper;
import com.msvanegasg.pruebaamaris.models.Fund;
import com.msvanegasg.pruebaamaris.repository.FundRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FundService {

    private final FundRepository repository;

    public FundService(FundRepository repository) {
        this.repository = repository;
    }

    public FundResponseDTO create(FundRequestDTO dto) {
        Fund fund = FundMapper.toEntity(dto);
        return FundMapper.toResponse(repository.save(fund));
    }

    public List<FundResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(FundMapper::toResponse)
                .collect(Collectors.toList());
    }

    public FundResponseDTO getById(String id) {
        return repository.findById(id)
                .map(FundMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Fund not found"));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public FundResponseDTO update(String id, FundRequestDTO dto) {
        Fund existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fund not found"));

        existing.setName(dto.getName());
        existing.setMinimumAmount(dto.getMinimumAmount());

        return FundMapper.toResponse(repository.save(existing));
    }
}
