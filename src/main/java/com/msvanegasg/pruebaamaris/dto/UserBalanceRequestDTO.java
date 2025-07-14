package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import jakarta.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBalanceRequestDTO {

    @NotBlank(message = "El ID del fondo no puede estar vacío")
    private String fundId;

    @NotBlank(message = "El ID del usuario no puede estar vacío")
    private String userId;

    @Min(value = 1, message = "El monto debe ser al menos de $1")
    private int amount;

    @NotNull(message = "El tipo de transacción es obligatorio")
    private TransactionType type;

    @NotNull(message = "El tipo de notificación es obligatorio")
    private NotificationType notificationType;
}
