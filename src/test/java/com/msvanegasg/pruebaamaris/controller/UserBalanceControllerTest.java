package com.msvanegasg.pruebaamaris.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msvanegasg.pruebaamaris.dto.UserBalanceRequestDTO;
import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import com.msvanegasg.pruebaamaris.service.UserBalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(UserBalanceController.class)
class UserBalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBalanceService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void suscribirAFondo_retorna200() throws Exception {
        var dto = new UserBalanceRequestDTO("u1", "f1", 150000, TransactionType.APERTURA, NotificationType.EMAIL);
        when(service.suscribirAFondo(any())).thenReturn(null);

        mockMvc.perform(post("/api/user-balance/suscribir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void cancelarFondo_retorna200() throws Exception {
        var dto = new UserBalanceRequestDTO("u1", "f1", 100000, TransactionType.CANCELACION, NotificationType.SMS);
        when(service.cancelarFondo(any())).thenReturn(null);

        mockMvc.perform(post("/api/user-balance/cancelar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void historial_retorna200() throws Exception {
        when(service.obtenerHistorial("u1")).thenReturn(List.of());

        mockMvc.perform(get("/api/user-balance/historial/u1"))
                .andExpect(status().isOk());
    }
}
