package com.msvanegasg.pruebaamaris.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msvanegasg.pruebaamaris.dto.UserBalanceRequestDTO;
import com.msvanegasg.pruebaamaris.dto.UserBalanceResponseDTO;
import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import com.msvanegasg.pruebaamaris.service.UserBalanceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserBalanceController.class)
class UserBalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBalanceService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void suscribirAFondo_retorna200_conContenido() throws Exception {
        var request = new UserBalanceRequestDTO("u1", "f1", 150000, TransactionType.APERTURA, NotificationType.EMAIL);
        var response = new UserBalanceResponseDTO("tx1", "u1", "f1", 150000, 350000, TransactionType.APERTURA, NotificationType.EMAIL, 123456789L);

        when(service.suscribirAFondo(any())).thenReturn(response);

        mockMvc.perform(post("/api/user-balance/suscribir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("tx1"))
                .andExpect(jsonPath("$.userId").value("u1"))
                .andExpect(jsonPath("$.fundId").value("f1"))
                .andExpect(jsonPath("$.amount").value(150000));
    }

    @Test
    void cancelarFondo_retorna200_conContenido() throws Exception {
        var request = new UserBalanceRequestDTO("u1", "f1", 100000, TransactionType.CANCELACION, NotificationType.SMS);
        var response = new UserBalanceResponseDTO("tx2", "u1", "f1", 100000, 600000, TransactionType.CANCELACION, NotificationType.SMS, 123456790L);

        when(service.cancelarFondo(any())).thenReturn(response);

        mockMvc.perform(post("/api/user-balance/cancelar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("tx2"))
                .andExpect(jsonPath("$.balanceAfter").value(600000));
    }

    @Test
    void historial_retorna200_conListaVacia() throws Exception {
        when(service.obtenerHistorial("u1")).thenReturn(List.of());

        mockMvc.perform(get("/api/user-balance/historial/u1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
