package com.msvanegasg.pruebaamaris.service;

import com.msvanegasg.pruebaamaris.dto.FundRequestDTO;
import com.msvanegasg.pruebaamaris.dto.FundResponseDTO;
import com.msvanegasg.pruebaamaris.mapper.FundMapper;
import com.msvanegasg.pruebaamaris.models.Fund;
import com.msvanegasg.pruebaamaris.repository.FundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        FundRequestDTO dto = new FundRequestDTO("Fund Name", 1000);
        Fund fund = Fund.builder()
                .fundId("generated-id-123")
                .name(dto.getName())
                .minimumAmount(dto.getMinimumAmount())
                .build();

        when(repository.save(any(Fund.class))).thenReturn(fund);

        FundResponseDTO result = service.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("generated-id-123");
        assertThat(result.getName()).isEqualTo(dto.getName());
        assertThat(result.getMinimumAmount()).isEqualTo(dto.getMinimumAmount());

        ArgumentCaptor<Fund> captor = ArgumentCaptor.forClass(Fund.class);
        verify(repository).save(captor.capture());

        assertThat(captor.getValue().getName()).isEqualTo(dto.getName());
        assertThat(captor.getValue().getMinimumAmount()).isEqualTo(dto.getMinimumAmount());
        assertThat(captor.getValue().getFundId()).isNotNull();
    }


    @Test
    void testGetAllFunds() {
        Fund fund1 = new Fund("id1", "Fund A", 100);
        Fund fund2 = new Fund("id2", "Fund B", 200);
        when(repository.findAll()).thenReturn(List.of(fund1, fund2));

        List<FundResponseDTO> funds = service.getAll();

        assertThat(funds).hasSize(2);
        assertThat(funds.get(0).getId()).isEqualTo("id1");
        assertThat(funds.get(1).getId()).isEqualTo("id2");
    }

    @Test
    void testGetByIdFound() {
        Fund fund = new Fund("id1", "Fund X", 500);
        when(repository.findById("id1")).thenReturn(Optional.of(fund));

        FundResponseDTO result = service.getById("id1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("id1");
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById("idX")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.getById("idX");
        });

        assertThat(exception.getMessage()).isEqualTo("Fund not found");
    }

    @Test
    void testDeleteFund() {
        doNothing().when(repository).deleteById("id1");

        service.delete("id1");

        verify(repository, times(1)).deleteById("id1");
    }
    
    @Test
    void testUpdateFund() {
        String id = "F001";
        Fund existingFund = new Fund(id, "Old Name", 1000);
        FundRequestDTO updateDto = new FundRequestDTO("New Name", 2000);
        Fund updatedFund = new Fund(id, updateDto.getName(), updateDto.getMinimumAmount());

        when(repository.findById(id)).thenReturn(Optional.of(existingFund));
        when(repository.save(any(Fund.class))).thenReturn(updatedFund);

        FundResponseDTO response = service.update(id, updateDto);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getName()).isEqualTo(updateDto.getName());
        assertThat(response.getMinimumAmount()).isEqualTo(updateDto.getMinimumAmount());

        verify(repository).save(existingFund);
    }

    @Test
    void testUpdateFund_NotFound() {
        FundRequestDTO dto = new FundRequestDTO("Name", 1000);
        when(repository.findById("F999")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.update("F999", dto);
        });

        assertThat(ex.getMessage()).isEqualTo("Fund not found");
    }

}
