package com.msvanegasg.pruebaamaris.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msvanegasg.pruebaamaris.dto.FundRequestDTO;
import com.msvanegasg.pruebaamaris.dto.FundResponseDTO;
import com.msvanegasg.pruebaamaris.enums.FundCategory;
import com.msvanegasg.pruebaamaris.service.FundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FundController.class)
public class FundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FundService fundService;

    @Autowired
    private ObjectMapper objectMapper;

    private FundResponseDTO mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = new FundResponseDTO("F001", "Fondo Ejemplo", 1500, FundCategory.FPV);
    }

    @Test
    void testCreateFund() throws Exception {
        FundRequestDTO request = new FundRequestDTO("Fondo Ejemplo", 1500, FundCategory.FPV);

        when(fundService.create(any(FundRequestDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("F001"))
                .andExpect(jsonPath("$.name").value("Fondo Ejemplo"))
                .andExpect(jsonPath("$.minimumAmount").value(1500))
                .andExpect(jsonPath("$.category").value("FPV"));
    }

    @Test
    void testGetAllFunds() throws Exception {
        when(fundService.getAll()).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/api/funds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("F001"))
                .andExpect(jsonPath("$[0].name").value("Fondo Ejemplo"))
                .andExpect(jsonPath("$[0].minimumAmount").value(1500))
                .andExpect(jsonPath("$[0].category").value("FPV"));
    }

    @Test
    void testGetById() throws Exception {
        when(fundService.getById("F001")).thenReturn(mockResponse);

        mockMvc.perform(get("/api/funds/F001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("F001"))
                .andExpect(jsonPath("$.category").value("FPV"));
    }

    @Test
    void testDeleteFund() throws Exception {
        doNothing().when(fundService).delete("F001");

        mockMvc.perform(delete("/api/funds/F001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateFundWithBlankName() throws Exception {
        FundRequestDTO invalidRequest = new FundRequestDTO("   ", 1500, FundCategory.FIC);

        mockMvc.perform(post("/api/funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("El nombre del fondo es obligatorio"));
    }

    @Test
    void testCreateFundWithInvalidMinimumAmount() throws Exception {
        FundRequestDTO invalidRequest = new FundRequestDTO("Fondo Test", 0, FundCategory.FIC);

        mockMvc.perform(post("/api/funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.minimumAmount").value("El monto mínimo debe ser mayor que cero"));
    }

    @Test
    void testUpdateFund() throws Exception {
        FundRequestDTO updateDto = new FundRequestDTO("Updated Name", 2500, FundCategory.FIC);
        FundResponseDTO updatedResponse = new FundResponseDTO("F001", "Updated Name", 2500, FundCategory.FIC);

        when(fundService.update(Mockito.eq("F001"), any(FundRequestDTO.class))).thenReturn(updatedResponse);

        mockMvc.perform(put("/api/funds/F001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("F001"))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.minimumAmount").value(2500))
                .andExpect(jsonPath("$.category").value("FIC"));
    }

    @Test
    void testUpdateFund_NotFound() throws Exception {
        FundRequestDTO request = new FundRequestDTO("Name", 1000, FundCategory.FPV);

        when(fundService.update(Mockito.eq("F999"), any(FundRequestDTO.class)))
                .thenThrow(new RuntimeException("Fund not found"));

        mockMvc.perform(put("/api/funds/F999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Fund not found"));
    }
}
