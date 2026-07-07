package com.banco.exception;

public class OverlappingReservationException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public OverlappingReservationException(String message) {
        super(message);
    }
}