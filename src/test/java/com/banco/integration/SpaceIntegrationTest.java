package com.banco.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.banco.entity.Space;
import com.banco.enums.SpaceType;
import com.banco.repository.SpaceRepository;


@SpringBootTest
@ActiveProfiles("test")
class SpaceIntegrationTest {


    @Autowired
    private SpaceRepository repository;



    @Test
    void shouldSaveSpaceInDatabase(){


        Space space = Space.builder()
                .name("Sala Test")
                .type(SpaceType.MEETING_ROOM)
                .capacity(10)
                .location("Piso 1")
                .hourlyRate(new BigDecimal("25"))
                .active(true)
                .build();



        Space saved =
                repository.save(space);



        assertNotNull(saved.getId());

        assertEquals(
                "Sala Test",
                saved.getName()
        );

    }

}