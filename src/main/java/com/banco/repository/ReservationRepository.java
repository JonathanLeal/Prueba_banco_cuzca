package com.banco.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banco.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r JOIN FETCH r.user JOIN FETCH r.space")
    List<Reservation> findAllWithAssociations();

    @Query("SELECT r FROM Reservation r JOIN FETCH r.user JOIN FETCH r.space WHERE r.user.id = :userId")
    List<Reservation> findByUserIdWithAssociations(@Param("userId") Long userId);

    @Query("SELECT r FROM Reservation r JOIN FETCH r.user JOIN FETCH r.space WHERE r.id = :id")
    Optional<Reservation> findByIdWithAssociations(@Param("id") Long id);
    
    List<Reservation> findBySpaceId(Long spaceId);

    boolean existsBySpaceIdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long spaceId,
            LocalDateTime endTime,
            LocalDateTime startTime);
}