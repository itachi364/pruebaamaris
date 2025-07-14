package com.msvanegasg.pruebaamaris.mapper;

import com.msvanegasg.pruebaamaris.dto.UserBalanceRequestDTO;
import com.msvanegasg.pruebaamaris.dto.UserBalanceResponseDTO;
import com.msvanegasg.pruebaamaris.models.UserBalance;

import java.time.Instant;
import java.util.UUID;

public class UserBalanceMapper {

    public static UserBalance toModel(UserBalanceRequestDTO dto, int balanceAfter) {
        return UserBalance.builder()
                .id(UUID.randomUUID().toString())
                .fundId(dto.getFundId())
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .balanceAfter(balanceAfter)
                .type(dto.getType())
                .notificationType(dto.getNotificationType())
                .timestamp(Instant.now().toEpochMilli())
                .build();
    }

    public static UserBalanceResponseDTO toResponse(UserBalance model) {
        return UserBalanceResponseDTO.builder()
                .id(model.getId())
                .fundId(model.getFundId())
                .userId(model.getUserId())
                .amount(model.getAmount())
                .balanceAfter(model.getBalanceAfter())
                .type(model.getType())
                .notificationType(model.getNotificationType())
                .timestamp(model.getTimestamp())
                .build();
    }
}

