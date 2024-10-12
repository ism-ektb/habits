package ru.ism.testBank.annotation.startBeforEnd;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD, CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartIsBeforeEnd.class)
public @interface ValidStartIsBeforeEnd {
    String message() default "Начало должно быть раньше конца запроса";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}