package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.TransactionRequestDTO;
import com.msvanegasg.pruebaamaris.dto.TransactionResponseDTO;
import com.msvanegasg.pruebaamaris.exception.BadRequestException;
import com.msvanegasg.pruebaamaris.mapper.TransactionMapper;
import com.msvanegasg.pruebaamaris.models.Transaction;
import com.msvanegasg.pruebaamaris.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static final int MONTO_INICIAL = 500_000;

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public TransactionResponseDTO registrar(TransactionRequestDTO dto) {
        if (dto.getAmount() <= 0) {
            throw new BadRequestException("El monto debe ser mayor a cero");
        }

        int balanceActual = calcularBalanceActual(dto.getUserId());
        int balanceFinal;

        switch (dto.getType()) {
            case APERTURA -> {
                if (balanceActual < dto.getAmount()) {
                    throw new BadRequestException("No tiene saldo disponible para vincularse al fondo " + dto.getFundId());
                }
                balanceFinal = balanceActual - dto.getAmount();
            }
            case CANCELACION -> {
                balanceFinal = balanceActual + dto.getAmount();
            }
            default -> throw new BadRequestException("Tipo de transacción no válido");
        }

        Transaction transaction = TransactionMapper.toModel(dto, balanceFinal);
        return TransactionMapper.toResponse(repository.save(transaction));
    }

    public List<TransactionResponseDTO> obtenerHistorial(String userId) {
        return repository.findByUserId(userId).stream()
                .sorted((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()))
                .map(TransactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    private int calcularBalanceActual(String userId) {
        return repository.findByUserId(userId).stream()
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .map(Transaction::getBalanceAfter)
                .reduce((first, second) -> second)
                .orElse(MONTO_INICIAL);
    }
}
