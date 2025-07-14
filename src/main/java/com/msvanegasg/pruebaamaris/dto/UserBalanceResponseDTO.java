package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBalanceResponseDTO {
    private String id;
    private String fundId;
    private String userId;
    private int amount;
    private int balanceAfter;
    private TransactionType type;
    private NotificationType notificationType;
    private long timestamp;
}
