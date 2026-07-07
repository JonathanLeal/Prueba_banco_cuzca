package com.banco.client.payment;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PaymentClient {

    public boolean validatePayment() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return new Random().nextInt(10) < 7;
    }
}