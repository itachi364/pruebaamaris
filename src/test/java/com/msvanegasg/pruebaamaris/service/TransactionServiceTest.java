package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.TransactionRequestDTO;
import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import com.msvanegasg.pruebaamaris.exception.BadRequestException;
import com.msvanegasg.pruebaamaris.models.Transaction;
import com.msvanegasg.pruebaamaris.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionRepository repository;
    private TransactionService service;

    @BeforeEach
    void setup() {
        repository = mock(TransactionRepository.class);
        service = new TransactionService(repository);
    }

    @Test
    void registrar_aperturaValida_devuelveDTO() {
        var request = new TransactionRequestDTO("user1", "fund1", 200000, TransactionType.APERTURA, NotificationType.EMAIL);
        when(repository.findByUserId("user1")).thenReturn(List.of());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.registrar(request);

        assertEquals("user1", result.getUserId());
        assertEquals("fund1", result.getFundId());
        assertEquals(200000, result.getAmount());
    }

    @Test
    void registrar_cancelacionValida_devuelveDTO() {
        var request = new TransactionRequestDTO("user1", "fund1", 100000, TransactionType.CANCELACION, NotificationType.SMS);
        when(repository.findByUserId("user1")).thenReturn(List.of());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.registrar(request);

        assertEquals("user1", result.getUserId());
        assertEquals(100000, result.getAmount());
        assertEquals(TransactionType.CANCELACION, result.getType());
    }

    @Test
    void registrar_conMontoInvalido_lanzaExcepcion() {
        var request = new TransactionRequestDTO("user1", "fund1", 0, TransactionType.APERTURA, NotificationType.EMAIL);

        var ex = assertThrows(BadRequestException.class, () -> service.registrar(request));
        assertEquals("El monto debe ser mayor a cero", ex.getMessage());
    }

    @Test
    void registrar_conSaldoInsuficiente_lanzaExcepcion() {
        var request = new TransactionRequestDTO("user1", "fundX", 600000, TransactionType.APERTURA, NotificationType.EMAIL);
        when(repository.findByUserId("user1")).thenReturn(List.of());

        var ex = assertThrows(BadRequestException.class, () -> service.registrar(request));
        assertEquals("No tiene saldo disponible para vincularse al fondo fundX", ex.getMessage());
    }

    @Test
    void obtenerHistorial_retornaListaOrdenada() {
        var t1 = Transaction.builder().timestamp(1).build();
        var t2 = Transaction.builder().timestamp(2).build();

        when(repository.findByUserId("user1")).thenReturn(List.of(t1, t2));

        var result = service.obtenerHistorial("user1");

        assertEquals(2, result.size());
        assertTrue(result.get(0).getTimestamp() > result.get(1).getTimestamp());
    }
}
