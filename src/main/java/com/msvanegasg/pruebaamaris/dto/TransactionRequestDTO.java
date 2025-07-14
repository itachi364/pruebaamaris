package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    @NotBlank
    private String userId;

    @NotBlank
    private String fundId;

    @Min(value = 1, message = "El monto debe ser mayor que cero")
    private int amount;

    private TransactionType type;
    private NotificationType notificationType;
}
