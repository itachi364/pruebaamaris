package com.msvanegasg.pruebaamaris.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundRequestDTO {

    @NotBlank(message = "El nombre del fondo es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    @Schema(description = "Nombre del fondo", example = "Fondo Conservador")
    private String name;

    @Min(value = 100_000, message = "El monto mínimo para vincularse debe ser al menos COP $100,000")
    @Schema(description = "Monto mínimo para vincularse al fondo", example = "100000")
    private int minimumAmount;
}
