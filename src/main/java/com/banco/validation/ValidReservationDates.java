package com.banco.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

@Target({ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservationDatesValidator.class)
@Documented
public @interface ValidReservationDates { 
    String message() default "La fecha de fin debe ser posterior a la fecha de inicio y no puede ser en el pasado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}