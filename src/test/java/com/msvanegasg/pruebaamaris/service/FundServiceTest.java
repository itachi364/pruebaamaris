package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.FundRequestDTO;
import com.msvanegasg.pruebaamaris.dto.FundResponseDTO;
import com.msvanegasg.pruebaamaris.enums.FundCategory;
import com.msvanegasg.pruebaamaris.mapper.FundMapper;
import com.msvanegasg.pruebaamaris.models.Fund;
import com.msvanegasg.pruebaamaris.repository.FundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FundServiceTest {

    @Mock
    private FundRepository repository;

    @InjectMocks
    private FundService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFund() {
        FundRequestDTO dto = new FundRequestDTO("Fondo Test", 1500,FundCategory.FIC);
        Fund fund = Fund.builder()
                .fundId("auto-generated-id")
                .name(dto.getName())
                .minimumAmount(dto.getMinimumAmount())
                .build();

        when(repository.save(any(Fund.class))).thenReturn(fund);

        FundResponseDTO result = service.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(dto.getName());
        assertThat(result.getMinimumAmount()).isEqualTo(dto.getMinimumAmount());
    }

    @Test
    void testGetAllFunds() {
        Fund fund1 = new Fund("f1", "Fondo A", 1000,FundCategory.FIC);
        Fund fund2 = new Fund("f2", "Fondo B", 2000,FundCategory.FPV);
        when(repository.findAll()).thenReturn(List.of(fund1, fund2));

        List<FundResponseDTO> result = service.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo("f1");
        assertThat(result.get(1).getId()).isEqualTo("f2");
    }

    @Test
    void testGetByIdFound() {
        Fund fund = new Fund("f1", "Fondo X", 500, FundCategory.FIC);
        when(repository.findById("f1")).thenReturn(Optional.of(fund));

        FundResponseDTO result = service.getById("f1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("f1");
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById("fX")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.getById("fX");
        });

        assertThat(ex.getMessage()).isEqualTo("Fund not found");
    }

    @Test
    void testDeleteFund() {
        doNothing().when(repository).deleteById("f1");

        service.delete("f1");

        verify(repository).deleteById("f1");
    }

    @Test
    void testUpdateFund() {
        String id = "f1";
        Fund existing = new Fund(id, "Antiguo", 1000,FundCategory.FIC);
        FundRequestDTO dto = new FundRequestDTO("Nuevo Fondo", 2500, FundCategory.FPV);
        Fund updated = new Fund(id, dto.getName(), dto.getMinimumAmount(),dto.getCategory());

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(updated);

        FundResponseDTO result = service.update(id, dto);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Nuevo Fondo");
        assertThat(result.getMinimumAmount()).isEqualTo(2500);
    }

    @Test
    void testUpdateFund_NotFound() {
        FundRequestDTO dto = new FundRequestDTO("Fondo Z", 1000, FundCategory.FIC);
        when(repository.findById("Z999")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.update("Z999", dto);
        });

        assertThat(ex.getMessage()).isEqualTo("Fund not found");
    }
}
