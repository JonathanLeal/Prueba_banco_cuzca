package com.banco.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banco.dto.CreateSpaceRequestDTO;
import com.banco.dto.SpaceResponseDTO;
import com.banco.entity.Space;
import com.banco.enums.SpaceType;
import com.banco.repository.SpaceRepository;
import com.banco.serviceImplement.SpaceServiceImpl;


@ExtendWith(MockitoExtension.class)
class SpaceServiceImplTest {


    @Mock
    private SpaceRepository repository;

    @InjectMocks
    private SpaceServiceImpl service;


    @Test
    void shouldCreateSpaceSuccessfully() {

        CreateSpaceRequestDTO dto = new CreateSpaceRequestDTO();
        dto.setName("Sala Ejecutiva");
        dto.setType(SpaceType.PRIVATE_OFFICE);
        dto.setCapacity(5);
        dto.setLocation("Piso 2");
        dto.setHourlyRate(new BigDecimal("20"));


        Space saved = Space.builder()
                .id(1L)
                .name("Sala Ejecutiva")
                .type(SpaceType.PRIVATE_OFFICE)
                .capacity(5)
                .location("Piso 2")
                .hourlyRate(new BigDecimal("20"))
                .active(true)
                .build();


        when(repository.save(any(Space.class)))
                .thenReturn(saved);


        SpaceResponseDTO response = service.create(dto);


        assertNotNull(response);
        verify(repository).save(any(Space.class));
    }
}