package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.UserBalanceRequestDTO;
import com.msvanegasg.pruebaamaris.dto.UserBalanceResponseDTO;
import com.msvanegasg.pruebaamaris.enums.TransactionType;
import com.msvanegasg.pruebaamaris.exception.BadRequestException;
import com.msvanegasg.pruebaamaris.mapper.UserBalanceMapper;
import com.msvanegasg.pruebaamaris.models.UserBalance;
import com.msvanegasg.pruebaamaris.repository.UserBalanceRepository;
import com.msvanegasg.pruebaamaris.repository.FundRepository;
import com.msvanegasg.pruebaamaris.repository.TransactionRepository;
import com.msvanegasg.pruebaamaris.models.Fund;
import com.msvanegasg.pruebaamaris.models.Transaction;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserBalanceService {

	private static final int MONTO_INICIAL = 500_000;

	private final UserBalanceRepository repository;
	private final FundRepository fundRepository;
	private final TransactionRepository transactionRepository;

	public UserBalanceService(UserBalanceRepository repository, FundRepository fundRepository,
			TransactionRepository transactionRepository) {
		this.repository = repository;
		this.fundRepository = fundRepository;
		this.transactionRepository = transactionRepository;
	}

	public UserBalanceResponseDTO suscribirAFondo(UserBalanceRequestDTO dto) {
		if (dto.getType() != TransactionType.APERTURA) {
			throw new BadRequestException("Tipo de transacción inválido para Apertura.");
		}

		Fund fund = fundRepository.findById(dto.getFundId())
				.orElseThrow(() -> new BadRequestException("Fondo no encontrado con ID: " + dto.getFundId()));

		int montoMinimo = fund.getMinimumAmount();
		int balanceActual = calcularBalanceActual(dto.getUserId());

		if (dto.getAmount() < montoMinimo) {
			throw new BadRequestException(
					"El monto mínimo para vincularse al fondo " + fund.getName() + " es de COP $" + montoMinimo);
		}

		if (balanceActual < dto.getAmount()) {
			throw new BadRequestException("No tiene saldo disponible para vincularse al fondo " + fund.getName());
		}

		UserBalance nuevaTransaccion = UserBalanceMapper.toModel(dto, balanceActual - dto.getAmount());
		UserBalance savedBalance = repository.save(nuevaTransaccion);

		Transaction transaction = Transaction.builder().transactionId(UUID.randomUUID().toString())
				.userId(dto.getUserId()).fundId(dto.getFundId()).amount(dto.getAmount()).type(dto.getType())
				.notificationType(dto.getNotificationType()).timestamp(System.currentTimeMillis()).build();

		transactionRepository.save(transaction);

		return UserBalanceMapper.toResponse(savedBalance);
	}

	public UserBalanceResponseDTO cancelarFondo(UserBalanceRequestDTO dto) {
		if (dto.getType() != TransactionType.CANCELACION) {
			throw new BadRequestException("Tipo de transacción inválido para cancelación.");
		}

		Fund fund = fundRepository.findById(dto.getFundId())
				.orElseThrow(() -> new BadRequestException("Fondo no encontrado con ID: " + dto.getFundId()));

		int balanceActual = calcularBalanceActual(dto.getUserId());

		UserBalance nuevaTransaccion = UserBalanceMapper.toModel(dto, balanceActual + dto.getAmount());
		UserBalance savedBalance = repository.save(nuevaTransaccion);

		Transaction transaction = Transaction.builder().transactionId(UUID.randomUUID().toString())
				.userId(dto.getUserId()).fundId(dto.getFundId()).amount(dto.getAmount()).type(dto.getType())
				.notificationType(dto.getNotificationType()).timestamp(System.currentTimeMillis()).build();

		transactionRepository.save(transaction);

		return UserBalanceMapper.toResponse(savedBalance);
	}

	public List<UserBalanceResponseDTO> obtenerHistorial(String userId) {
		return repository.findByUserId(userId).stream()
				.sorted((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp())).map(UserBalanceMapper::toResponse)
				.collect(Collectors.toList());
	}

	private int calcularBalanceActual(String userId) {
		List<UserBalance> transacciones = repository.findByUserId(userId);
		return transacciones.stream().sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
				.map(UserBalance::getBalanceAfter).reduce((first, second) -> second).orElse(MONTO_INICIAL);
	}
}
