package com.banco.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.banco.entity.User;
import com.banco.enums.Role;
import com.banco.repository.UserRepository;



@SpringBootTest
@ActiveProfiles("test")
class UserIntegrationTest {


    @Autowired
    private UserRepository repository;



    @Test
    void shouldFindUserByEmail(){


        User user = User.builder()
                .firstName("Juan")
                .lastName("Perez")
                .email("juan@test.com")
                .password("123456")
                .role(Role.USER)
                .enabled(true)
                .build();



        repository.save(user);



        User result =
                repository.findByEmail(
                        "juan@test.com"
                ).orElse(null);



        assertNotNull(result);


        assertEquals(
                "Juan",
                result.getFirstName()
        );


    }

}