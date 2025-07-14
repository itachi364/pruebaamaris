package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para registrar una transacción del usuario en un fondo.")
public class TransactionRequestDTO {

    @NotBlank(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID del usuario que realiza la transacción", example = "user123")
    private String userId;

    @NotBlank(message = "El ID del fondo es obligatorio")
    @Schema(description = "ID del fondo al que se vincula o del que se retira el usuario", example = "fund456")
    private String fundId;

    @Min(value = 1, message = "El monto debe ser mayor a 0")
    @Schema(description = "Monto involucrado en la transacción", example = "150000")
    private int amount;

    @NotNull(message = "El tipo de transacción es obligatorio")
    @Schema(description = "Tipo de transacción", example = "APERTURA")
    private TransactionType type;

    @NotNull(message = "El tipo de notificación es obligatorio")
    @Schema(description = "Tipo de notificación a enviar al usuario", example = "EMAIL")
    private NotificationType notificationType;
}
