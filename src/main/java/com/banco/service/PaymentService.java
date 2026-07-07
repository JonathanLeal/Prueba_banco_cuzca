package com.banco.service;

import org.springframework.stereotype.Service;

import com.banco.client.payment.PaymentClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class PaymentService {

    private final PaymentClient paymentClient;

    public PaymentService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public boolean processPayment() {

        return paymentClient.validatePayment();
    }

    public boolean paymentFallback(Exception ex) {

        System.out.println("⚠ Circuit breaker OPEN - fallback ejecutado");
        return false;
    }
}