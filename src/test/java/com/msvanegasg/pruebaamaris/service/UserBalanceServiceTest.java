package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.UserBalanceRequestDTO;
import com.msvanegasg.pruebaamaris.enums.NotificationType;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import com.msvanegasg.pruebaamaris.exception.BadRequestException;
import com.msvanegasg.pruebaamaris.models.Fund;
import com.msvanegasg.pruebaamaris.models.UserBalance;
import com.msvanegasg.pruebaamaris.repository.FundRepository;
import com.msvanegasg.pruebaamaris.repository.UserBalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserBalanceServiceTest {

    private UserBalanceRepository repository;
    private FundRepository fundRepository;
    private UserBalanceService service;

    @BeforeEach
    void setup() {
        repository = mock(UserBalanceRepository.class);
        fundRepository = mock(FundRepository.class);
        service = new UserBalanceService(repository, fundRepository);
    }

    @Test
    void suscribirAFondo_conDatosValidos_retornaDTO() {
        var request = new UserBalanceRequestDTO("u1", "f1", 200000, TransactionType.APERTURA, NotificationType.EMAIL);

        when(fundRepository.findById("f1"))
                .thenReturn(Optional.of(new Fund("f1", "Fondo Conservador", 100000)));
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
    void suscribirAFondo_conFondoInexistente_lanzaExcepcion() {
        var request = new UserBalanceRequestDTO("u1", "fX", 200000, TransactionType.APERTURA, NotificationType.EMAIL);

        when(fundRepository.findById("fX")).thenReturn(Optional.empty());

        var ex = assertThrows(BadRequestException.class, () -> service.suscribirAFondo(request));
        assertEquals("Fund not found", ex.getMessage());
    }

    @Test
    void suscribirAFondo_conMontoInferiorAlMinimo_lanzaExcepcion() {
        var request = new UserBalanceRequestDTO("u1", "f1", 50_000, TransactionType.APERTURA, NotificationType.EMAIL);

        when(fundRepository.findById("f1"))
                .thenReturn(Optional.of(new Fund("f1", "Fondo Conservador", 100000)));

        var ex = assertThrows(BadRequestException.class, () -> service.suscribirAFondo(request));
        assertEquals("El monto mínimo para vincularse es de COP $100.000.", ex.getMessage());
    }

    @Test
    void suscribirAFondo_conSaldoInsuficiente_lanzaExcepcion() {
        var request = new UserBalanceRequestDTO("u1", "f1", 600000, TransactionType.APERTURA, NotificationType.EMAIL);

        when(fundRepository.findById("f1"))
                .thenReturn(Optional.of(new Fund("f1", "Fondo Conservador", 100000)));
        when(repository.findByUserId("u1")).thenReturn(List.of());

        var ex = assertThrows(BadRequestException.class, () -> service.suscribirAFondo(request));
        assertEquals("No tiene saldo disponible para vincularse al fondo Fondo Conservador", ex.getMessage());
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
