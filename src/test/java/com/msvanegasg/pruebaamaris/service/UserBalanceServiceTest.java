package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.UserBalanceRequestDTO;
import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import com.msvanegasg.pruebaamaris.exception.BadRequestException;
import com.msvanegasg.pruebaamaris.models.UserBalance;
import com.msvanegasg.pruebaamaris.repository.UserBalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserBalanceServiceTest {

    private UserBalanceRepository repository;
    private UserBalanceService service;

    @BeforeEach
    void setup() {
        repository = mock(UserBalanceRepository.class);
        service = new UserBalanceService(repository);
    }

    @Test
    void suscribirAFondo_conDatosValidos_retornaDTO() {
        var request = new UserBalanceRequestDTO("u1", "f1", 200000, TransactionType.APERTURA, NotificationType.EMAIL);

        when(repository.findByUserId("u1")).thenReturn(List.of());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.suscribirAFondo(request);

        assertEquals("u1", result.getUserId());
        assertEquals("f1", result.getFundId());
        assertEquals(200000, result.getAmount());
        verify(repository).save(any());
    }

    @Test
    void suscribirAFondo_conTipoInvalido_lanzaExcepcion() {
        var request = new UserBalanceRequestDTO("u1", "f1", 200000, TransactionType.CANCELACION, NotificationType.EMAIL);

        var ex = assertThrows(BadRequestException.class, () -> service.suscribirAFondo(request));
        assertEquals("Tipo de transacción inválido para Apertura.", ex.getMessage());
    }

    @Test
    void cancelarFondo_conDatosValidos_retornaDTO() {
        var request = new UserBalanceRequestDTO("u1", "f1", 100000, TransactionType.CANCELACION, NotificationType.SMS);

        when(repository.findByUserId("u1")).thenReturn(List.of());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.cancelarFondo(request);

        assertEquals("u1", result.getUserId());
        assertEquals("f1", result.getFundId());
        assertEquals(100000, result.getAmount());
        verify(repository).save(any());
    }

    @Test
    void cancelarFondo_conTipoInvalido_lanzaExcepcion() {
        var request = new UserBalanceRequestDTO("u1", "f1", 100000, TransactionType.APERTURA, NotificationType.SMS);

        var ex = assertThrows(BadRequestException.class, () -> service.cancelarFondo(request));
        assertEquals("Tipo de transacción inválido para cancelación.", ex.getMessage());
    }
}
