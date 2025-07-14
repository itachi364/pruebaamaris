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
                .category(dto.getCategory())
                .build();
    }

    public static FundResponseDTO toResponse(Fund fund) {
        return FundResponseDTO.builder()
                .id(fund.getFundId())
                .name(fund.getName())
                .minimumAmount(fund.getMinimumAmount())
                .category(fund.getCategory())
                .build();
    }
}
