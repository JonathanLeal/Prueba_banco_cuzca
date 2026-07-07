package com.banco.validation;

import com.banco.dto.CreateReservationRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ReservationDatesValidator implements ConstraintValidator<ValidReservationDates, CreateReservationRequestDTO> {

    @Override
    public boolean isValid(CreateReservationRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getStartTime() == null || dto.getEndTime() == null) {
            return true; 
        }

        LocalDateTime now = LocalDateTime.now();

        if (dto.getStartTime().isBefore(now)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La fecha de inicio no puede ser en el pasado.")
                   .addPropertyNode("startTime")
                   .addConstraintViolation();
            return false;
        }

        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La fecha de fin debe ser posterior a la fecha de inicio.")
                   .addPropertyNode("endTime")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}