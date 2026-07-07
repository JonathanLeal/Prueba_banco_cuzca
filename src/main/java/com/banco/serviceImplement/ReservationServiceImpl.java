package com.banco.serviceImplement;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.dto.CreateReservationRequestDTO;
import com.banco.dto.ReservationResponseDTO;
import com.banco.entity.Reservation;
import com.banco.entity.Space;
import com.banco.entity.User;
import com.banco.enums.PaymentStatus;
import com.banco.enums.ReservationStatus;
import com.banco.event.ReservationConfirmedEvent;
import com.banco.exception.OverlappingReservationException; 
import com.banco.exception.ResourceNotFoundException;      
import com.banco.mapper.ReservationMapper;
import com.banco.repository.ReservationRepository;
import com.banco.repository.SpaceRepository;
import com.banco.repository.UserRepository;
import com.banco.service.PaymentService;
import com.banco.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher eventPublisher;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            SpaceRepository spaceRepository,
            PaymentService paymentService,
            ApplicationEventPublisher eventPublisher) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
        this.paymentService = paymentService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public ReservationResponseDTO create(Long userId, CreateReservationRequestDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));

        Space space = spaceRepository.findById(dto.getSpaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Espacio de Coworking no encontrado con ID: " + dto.getSpaceId()));

        // Control de solapamiento
        boolean overlap = reservationRepository
                .existsBySpaceIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        dto.getSpaceId(),
                        dto.getEndTime(),
                        dto.getStartTime());

        if (overlap) {
            throw new OverlappingReservationException("El espacio ya se encuentra reservado en el rango de tiempo seleccionado.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSpace(space);
        reservation.setStartTime(dto.getStartTime());
        reservation.setEndTime(dto.getEndTime());

        reservation.setStatus(ReservationStatus.PENDING_PAYMENT);
        reservation.setPaymentStatus(PaymentStatus.PENDING);

        long hours = java.time.Duration.between(dto.getStartTime(), dto.getEndTime()).toHours();
        if (hours == 0) hours = 1; 
        BigDecimal total = space.getHourlyRate().multiply(BigDecimal.valueOf(hours));
        reservation.setTotalPrice(total);

        boolean paymentOk = paymentService.processPayment();

        if (paymentOk) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setPaymentStatus(PaymentStatus.APPROVED);
            
            Reservation saved = reservationRepository.save(reservation);
            eventPublisher.publishEvent(new ReservationConfirmedEvent(saved));
            return ReservationMapper.toDTO(saved);
        } else {
            reservation.setStatus(ReservationStatus.PENDING_PAYMENT);
            reservation.setPaymentStatus(PaymentStatus.PENDING);
            Reservation saved = reservationRepository.save(reservation);
            return ReservationMapper.toDTO(saved);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO findById(Long id) {
        Reservation res = reservationRepository.findByIdWithAssociations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        return ReservationMapper.toDTO(res);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findAll() {
        return reservationRepository.findAllWithAssociations()
                .stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findByUser(Long userId) {
        return reservationRepository.findByUserIdWithAssociations(userId)
                .stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancel(Long id) {
        Reservation res = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        res.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(res);
    }
    
    @Transactional
    public void cancelSecure(Long id, Long authenticationUserId, String userRole) {
        Reservation res = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        if (!"ADMIN".equals(userRole) && !res.getUser().getId().equals(authenticationUserId)) {
            throw new SecurityException("No tienes permisos para cancelar esta reserva.");
        }

        res.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(res);
    }
}