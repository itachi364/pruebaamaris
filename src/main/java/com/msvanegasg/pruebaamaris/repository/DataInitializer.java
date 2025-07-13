package com.msvanegasg.pruebaamaris.repository;

import com.msvanegasg.pruebaamaris.models.Fund;
import com.msvanegasg.pruebaamaris.repository.FundRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataInitializer {

    private final FundRepository repository;

    @PostConstruct
    public void init() {
        if (repository.findAll().isEmpty()) {
            repository.save(Fund.builder().fundId("F001").name("Fondo Conservador").minimumAmount(100000).build());
            repository.save(Fund.builder().fundId("F002").name("Fondo Moderado").minimumAmount(200000).build());
            repository.save(Fund.builder().fundId("F003").name("Fondo Agresivo").minimumAmount(300000).build());
            System.out.println("Datos de prueba insertados en la tabla Funds.");
        } else {
            System.out.println("La tabla Funds ya contiene datos.");
        }
    }
}
