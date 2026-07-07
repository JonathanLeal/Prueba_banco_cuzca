package com.banco.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SpaceType {
	MEETING_ROOM,
    DESK,
    PRIVATE_OFFICE;
	
	@JsonCreator
    public static SpaceType fromString(String value) {
        if (value == null) return null;
        try {
            return SpaceType.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de espacio no válido. Valores permitidos: MEETING_ROOM, DESK, PRIVATE_OFFICE");
        }
    }
}
