package com.msvanegasg.pruebaamaris.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundRequestDTO {

    @NotBlank(message = "El nombre del fondo es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String name;

    @Min(value = 1, message = "El monto mínimo debe ser mayor que cero")
    private int minimumAmount;
}
