package com.banco.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.context.ApplicationEventPublisher;

import com.banco.dto.CreateReservationRequestDTO;
import com.banco.dto.ReservationResponseDTO;

import com.banco.entity.Reservation;
import com.banco.entity.Space;
import com.banco.entity.User;

import com.banco.enums.Role;
import com.banco.enums.SpaceType;

import com.banco.repository.ReservationRepository;
import com.banco.repository.SpaceRepository;
import com.banco.repository.UserRepository;

import com.banco.service.PaymentService;
import com.banco.serviceImplement.ReservationServiceImpl;


@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {


    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpaceRepository spaceRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ApplicationEventPublisher eventPublisher;


    @InjectMocks
    private ReservationServiceImpl service;


    @Test
    void shouldCreateReservationWhenPaymentApproved() {


        User user = User.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Perez")
                .email("test@test.com")
                .role(Role.USER)
                .build();


        Space space = Space.builder()
                .id(1L)
                .name("Sala Ejecutiva")
                .type(SpaceType.PRIVATE_OFFICE)
                .capacity(5)
                .location("Piso 2")
                .hourlyRate(new BigDecimal("10"))
                .active(true)
                .build();


        CreateReservationRequestDTO dto =
                new CreateReservationRequestDTO();

        dto.setSpaceId(1L);

        dto.setStartTime(
                LocalDateTime.of(2026, 8, 10, 10, 0)
        );

        dto.setEndTime(
                LocalDateTime.of(2026, 8, 10, 12, 0)
        );


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        when(spaceRepository.findById(1L))
                .thenReturn(Optional.of(space));


        when(reservationRepository
                .existsBySpaceIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        any(Long.class),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class)))
                .thenReturn(false);



        when(paymentService.processPayment())
                .thenReturn(true);



        /*
         * IMPORTANTE:
         * El servicio hace:
         *
         * Reservation saved = reservationRepository.save(reservation);
         *
         * Entonces debemos devolver esa misma reserva
         */

        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> {

                    Reservation reservation =
                            invocation.getArgument(0);


                    reservation.setId(1L);


                    return reservation;
                });



        ReservationResponseDTO response =
                service.create(1L, dto);



        assertNotNull(response);

        assertNotNull(response.getId());


        verify(reservationRepository)
                .save(any(Reservation.class));


    }

}