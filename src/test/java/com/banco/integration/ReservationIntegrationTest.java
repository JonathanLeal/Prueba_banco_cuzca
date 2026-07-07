package com.banco.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.banco.entity.Reservation;
import com.banco.entity.Space;
import com.banco.entity.User;
import com.banco.enums.PaymentStatus;
import com.banco.enums.ReservationStatus;
import com.banco.enums.Role;
import com.banco.enums.SpaceType;
import com.banco.repository.ReservationRepository;
import com.banco.repository.SpaceRepository;
import com.banco.repository.UserRepository;


@SpringBootTest
@ActiveProfiles("test")
class ReservationIntegrationTest {


    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpaceRepository spaceRepository;


    @Test
    void shouldCreateReservationWithRelations() {


        User user = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@test.com")
                .password("123")
                .role(Role.USER)
                .build();


        userRepository.save(user);


        Space space = Space.builder()
                .name("Oficina Test")
                .type(SpaceType.PRIVATE_OFFICE)
                .capacity(4)
                .location("Piso 1")
                .hourlyRate(new BigDecimal("50"))
                .active(true)
                .build();


        spaceRepository.save(space);



        Reservation reservation = Reservation.builder()
                .user(user)
                .space(space)
                .startTime(
                    LocalDateTime.of(2026,8,1,10,0)
                )
                .endTime(
                    LocalDateTime.of(2026,8,1,12,0)
                )
                .status(ReservationStatus.PENDING_PAYMENT)
                .paymentStatus(PaymentStatus.PENDING)
                .totalPrice(new BigDecimal("100"))
                .build();



        Reservation saved =
                reservationRepository.save(reservation);



        assertNotNull(saved.getId());

        Reservation result =
                reservationRepository
                .findByIdWithAssociations(saved.getId())
                .orElseThrow();


        assertEquals(
            "Oficina Test",
            result.getSpace().getName()
        );

        assertEquals(
            "test@test.com",
            result.getUser().getEmail()
        );
    }
}