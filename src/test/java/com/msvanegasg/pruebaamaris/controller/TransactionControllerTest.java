package com.msvanegasg.pruebaamaris.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msvanegasg.pruebaamaris.dto.TransactionRequestDTO;
import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import com.msvanegasg.pruebaamaris.service.TransactionService;
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

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrarTransaccion_retorna200() throws Exception {
        var dto = new TransactionRequestDTO("u1", "f1", 200000, TransactionType.APERTURA, NotificationType.EMAIL);
        when(service.registrar(any())).thenReturn(null);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void historial_retorna200() throws Exception {
        when(service.obtenerHistorial("u1")).thenReturn(List.of());

        mockMvc.perform(get("/api/transactions/u1"))
                .andExpect(status().isOk());
    }
}
