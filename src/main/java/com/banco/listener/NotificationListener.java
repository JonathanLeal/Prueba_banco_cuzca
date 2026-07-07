package com.banco.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.banco.event.ReservationConfirmedEvent;

@Component
public class NotificationListener {

    @Async
    @EventListener
    public void handleReservationConfirmed(ReservationConfirmedEvent event) {

        System.out.println("📧 EMAIL SIMULADO:");
        System.out.println("Reserva confirmada para usuario: "
                + event.getReservation().getUser().getEmail());

        System.out.println("Espacio: "
                + event.getReservation().getSpace().getName());
    }
}