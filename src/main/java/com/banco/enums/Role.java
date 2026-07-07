package com.banco.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
	ADMIN,
    USER;
    
    @JsonCreator
    public static Role fromString(String value) {
        if (value == null) return null;
        try {
            return Role.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Rol no válido de entrada: " + value);
        }
    }
}