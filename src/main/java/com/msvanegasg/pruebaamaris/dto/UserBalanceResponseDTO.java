package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de respuesta con los datos de una transacción realizada por el usuario.")
public class UserBalanceResponseDTO {

    @Schema(description = "Identificador único de la transacción", example = "abc123-uuid")
    private String id;

    @Schema(description = "ID del usuario", example = "user123")
    private String userId;

    @Schema(description = "ID del fondo", example = "fund456")
    private String fundId;

    @Schema(description = "Monto de la transacción", example = "150000")
    private int amount;

    @Schema(description = "Saldo restante después de la transacción", example = "350000")
    private int balanceAfter;

    @Schema(description = "Tipo de transacción (APERTURA o CANCELACION)", example = "APERTURA")
    private TransactionType type;

    @Schema(description = "Tipo de notificación enviada (EMAIL o SMS)", example = "EMAIL")
    private NotificationType notificationType;

    @Schema(description = "Fecha y hora de la transacción en formato epoch millis", example = "1720900000000")
    private long timestamp;
}
