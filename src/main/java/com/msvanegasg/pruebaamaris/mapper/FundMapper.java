package com.msvanegasg.pruebaamaris.mapper;

import com.msvanegasg.pruebaamaris.dto.FundRequestDTO;
import com.msvanegasg.pruebaamaris.dto.FundResponseDTO;
import com.msvanegasg.pruebaamaris.models.Fund;

import java.util.UUID;

public class FundMapper {

    public static Fund toEntity(FundRequestDTO dto) {
        return Fund.builder()
                .fundId(UUID.randomUUID().toString())
                .name(dto.getName())
                .minimumAmount(dto.getMinimumAmount())
                .build();
    }

    public static FundResponseDTO toResponse(Fund entity) {
        return FundResponseDTO.builder()
                .id(entity.getFundId())
                .name(entity.getName())
                .minimumAmount(entity.getMinimumAmount())
                .build();
    }
}

