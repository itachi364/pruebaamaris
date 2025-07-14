package com.msvanegasg.pruebaamaris.dto;

import com.msvanegasg.pruebaamaris.enums.FundCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundRequestDTO {

    @NotBlank(message = "El nombre del fondo es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    @Schema(description = "Nombre del fondo", example = "FDO-ACCIONES")
    private String name;

    @Min(value = 1, message = "El monto mínimo debe ser mayor que cero")
    @Schema(description = "Monto mínimo de inversión", example = "75000")
    private int minimumAmount;

    @NotNull(message = "La categoría del fondo es obligatoria")
    @Schema(description = "Categoría del fondo (FPV o FIC)", example = "FIC")
    private FundCategory category;
}
