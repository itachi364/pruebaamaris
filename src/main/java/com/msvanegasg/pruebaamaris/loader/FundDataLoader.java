package com.msvanegasg.pruebaamaris.loader;

import com.msvanegasg.pruebaamaris.enums.FundCategory;
import com.msvanegasg.pruebaamaris.models.Fund;
import com.msvanegasg.pruebaamaris.repository.FundRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FundDataLoader {

    private final FundRepository repository;

    @PostConstruct
    public void preloadFunds() {
        List<Fund> initialFunds = List.of(
            new Fund("1", "FPV_EL CLIENTE_RECAUDADORA", 75000, FundCategory.FPV),
            new Fund("2", "FPV_EL CLIENTE_ECOPETROL", 125000, FundCategory.FPV),
            new Fund("3", "DEUDAPRIVADA", 50000, FundCategory.FIC),
            new Fund("4", "FDO-ACCIONES", 250000, FundCategory.FIC),
            new Fund("5", "FPV_EL CLIENTE_DINAMICA", 100000, FundCategory.FPV)
        );

        for (Fund fund : initialFunds) {
            repository.findById(fund.getFundId()).ifPresentOrElse(
                existing -> log.info("Fondo ya existe: {}", existing.getFundId()),
                () -> {
                    repository.save(fund);
                    log.info("Fondo insertado: {}", fund.getFundId());
                }
            );
        }
    }
}
