package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO que representa una transacción realizada por un usuario en un fondo.")
public class TransactionResponseDTO {

    @Schema(description = "Identificador único de la transacción", example = "tx-123456")
    private String id;

    @Schema(description = "ID del usuario que realizó la transacción", example = "user-001")
    private String userId;

    @Schema(description = "ID del fondo asociado a la transacción", example = "fund-abc")
    private String fundId;

    @Schema(description = "Monto de dinero involucrado en la transacción", example = "150000")
    private int amount;

    @Schema(description = "Saldo del usuario después de realizar la transacción", example = "350000")
    private int balanceAfter;

    @Schema(description = "Tipo de transacción", example = "APERTURA", implementation = TransactionType.class)
    private TransactionType type;

    @Schema(description = "Tipo de notificación que se envió al usuario", example = "EMAIL", implementation = NotificationType.class)
    private NotificationType notificationType;

    @Schema(description = "Timestamp de la transacción en milisegundos desde epoch", example = "1720972485000")
    private long timestamp;
}
