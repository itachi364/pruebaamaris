package com.msvanegasg.pruebaamaris.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum TransactionType {
    APERTURA,
    CANCELACION
}