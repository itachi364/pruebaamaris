package com.msvanegasg.pruebaamaris.mapper;

import com.msvanegasg.pruebaamaris.models.Fund;
import com.msvanegasg.pruebaamaris.dto.FundRequestDTO;
import com.msvanegasg.pruebaamaris.dto.FundResponseDTO;

import java.util.UUID;

public class FundMapper {

    public static Fund toEntity(FundRequestDTO dto) {
        return Fund.builder()
                .fundId(UUID.randomUUID().toString())
                .name(dto.getName())
                .minimumAmount(dto.getMinimumAmount())
                .build();
    }

    public static FundResponseDTO toResponse(Fund fund) {
        FundResponseDTO dto = new FundResponseDTO();
        dto.setId(fund.getFundId());
        dto.setName(fund.getName());
        dto.setMinimumAmount(fund.getMinimumAmount());
        return dto;
    }
}
