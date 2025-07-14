package com.msvanegasg.pruebaamaris.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundResponseDTO {

    @Schema(description = "ID del fondo", example = "f1234-abcd")
    private String id;

    @Schema(description = "Nombre del fondo", example = "Fondo Conservador")
    private String name;

    @Schema(description = "Monto mínimo para vincularse al fondo", example = "100000")
    private int minimumAmount;
}
