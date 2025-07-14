package com.msvanegasg.pruebaamaris.mapper;

import com.msvanegasg.pruebaamaris.dto.TransactionRequestDTO;
import com.msvanegasg.pruebaamaris.dto.TransactionResponseDTO;
import com.msvanegasg.pruebaamaris.models.Transaction;

import java.util.UUID;

public class TransactionMapper {

    public static Transaction toModel(TransactionRequestDTO dto, int balanceAfter) {
        return Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .userId(dto.getUserId())
                .fundId(dto.getFundId())
                .amount(dto.getAmount())
                .balanceAfter(balanceAfter)
                .type(dto.getType())
                .notificationType(dto.getNotificationType())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static TransactionResponseDTO toResponse(Transaction model) {
        return TransactionResponseDTO.builder()
                .id(model.getTransactionId())
                .userId(model.getUserId())
                .fundId(model.getFundId())
                .amount(model.getAmount())
                .balanceAfter(model.getBalanceAfter())
                .type(model.getType())
                .notificationType(model.getNotificationType())
                .timestamp(model.getTimestamp())
                .build();
    }
}
