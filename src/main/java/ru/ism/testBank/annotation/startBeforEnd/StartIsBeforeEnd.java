package ru.ism.testBank.annotation.startBeforEnd;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class StartIsBeforeEnd implements ConstraintValidator<ValidStartIsBeforeEnd, Object[]> {

    public boolean isValid(Object[] value, ConstraintValidatorContext constraintContext) {
        if (value[0] == null || value[1] == null) {
            return true;
        }
        if (!(value[0] instanceof LocalDate)
                || !(value[1] instanceof LocalDate)) {
            throw new IllegalArgumentException(
                    "Illegal method signature, expected two parameters of type LocalDate.");
        }
        return ((LocalDate) value[0]).isBefore((LocalDate) value[1]);
    }
}
