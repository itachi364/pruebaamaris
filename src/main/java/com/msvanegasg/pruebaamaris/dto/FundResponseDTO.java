package com.msvanegasg.pruebaamaris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundResponseDTO {
    private String id;
    private String name;
    private int minimumAmount;
}