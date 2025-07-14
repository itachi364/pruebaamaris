package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.FundCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundResponseDTO {

    @Schema(description = "Identificador único del fondo", example = "abc123")
    private String id;

    @Schema(description = "Nombre del fondo", example = "FDO-ACCIONES")
    private String name;

    @Schema(description = "Monto mínimo requerido para invertir en el fondo", example = "250000")
    private int minimumAmount;

    @Schema(description = "Categoría del fondo (FPV o FIC)", example = "FIC")
    private FundCategory category;
}
