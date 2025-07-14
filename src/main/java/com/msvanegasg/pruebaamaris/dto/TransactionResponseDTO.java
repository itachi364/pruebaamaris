package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private String id;
    private String userId;
    private String fundId;
    private int amount;
    private int balanceAfter;
    private TransactionType type;
    private NotificationType notificationType;
    private long timestamp;
}
