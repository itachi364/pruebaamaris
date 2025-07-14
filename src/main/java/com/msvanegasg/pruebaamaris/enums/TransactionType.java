package com.msvanegasg.pruebaamaris.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo de transacción del fondo")
public enum TransactionType {
    @Schema(description = "Suscripción a un fondo")
    APERTURA,

    @Schema(description = "Cancelación o retiro de un fondo")
    CANCELACION
}
