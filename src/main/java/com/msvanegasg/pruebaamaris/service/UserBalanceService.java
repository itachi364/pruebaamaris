package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.UserBalanceRequestDTO;
import com.msvanegasg.pruebaamaris.dto.UserBalanceResponseDTO;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import com.msvanegasg.pruebaamaris.exception.BadRequestException;
import com.msvanegasg.pruebaamaris.mapper.UserBalanceMapper;
import com.msvanegasg.pruebaamaris.models.UserBalance;
import com.msvanegasg.pruebaamaris.repository.UserBalanceRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBalanceService {

    private static final int MONTO_INICIAL = 500_000;
    private static final int MONTO_MINIMO_VINCULACION = 100_000;

    private final UserBalanceRepository repository;

    public UserBalanceService(UserBalanceRepository repository) {
        this.repository = repository;
    }

    public UserBalanceResponseDTO suscribirAFondo(UserBalanceRequestDTO dto) {
        if (dto.getType() != TransactionType.APERTURA) {
            throw new BadRequestException("Tipo de transacción inválido para Apertura.");
        }

        int balanceActual = calcularBalanceActual(dto.getUserId());

        if (dto.getAmount() < MONTO_MINIMO_VINCULACION) {
            throw new BadRequestException("El monto mínimo para vincularse es de COP $100.000.");
        }

        if (balanceActual < dto.getAmount()) {
            throw new BadRequestException("No tiene saldo disponible para vincularse al fondo " + dto.getFundId());
        }

        UserBalance nuevaTransaccion = UserBalanceMapper.toModel(dto, balanceActual - dto.getAmount());
        return UserBalanceMapper.toResponse(repository.save(nuevaTransaccion));
    }

    public UserBalanceResponseDTO cancelarFondo(UserBalanceRequestDTO dto) {
        if (dto.getType() != TransactionType.CANCELACION) {
            throw new BadRequestException("Tipo de transacción inválido para cancelación.");
        }

        int balanceActual = calcularBalanceActual(dto.getUserId());

        UserBalance nuevaTransaccion = UserBalanceMapper.toModel(dto, balanceActual + dto.getAmount());
        return UserBalanceMapper.toResponse(repository.save(nuevaTransaccion));
    }

    public List<UserBalanceResponseDTO> obtenerHistorial(String userId) {
        return repository.findByUserId(userId).stream()
                .sorted((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()))
                .map(UserBalanceMapper::toResponse)
                .collect(Collectors.toList());
    }

    private int calcularBalanceActual(String userId) {
        List<UserBalance> transacciones = repository.findByUserId(userId);
        return transacciones.stream()
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .map(UserBalance::getBalanceAfter)
                .reduce((first, second) -> second)
                .orElse(MONTO_INICIAL);
    }
}
