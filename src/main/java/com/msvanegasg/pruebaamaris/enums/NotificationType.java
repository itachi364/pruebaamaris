package com.msvanegasg.pruebaamaris.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo de notificación a enviar")
public enum NotificationType {
    @Schema(description = "Notificación por correo electrónico")
    EMAIL,

    @Schema(description = "Notificación por mensaje SMS")
    SMS
}
