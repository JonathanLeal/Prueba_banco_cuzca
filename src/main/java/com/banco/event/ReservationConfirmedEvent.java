package com.banco.event;

import com.banco.entity.Reservation;

public class ReservationConfirmedEvent {

    private final Reservation reservation;

    public ReservationConfirmedEvent(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}