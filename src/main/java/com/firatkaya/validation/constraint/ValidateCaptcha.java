package com.firatkaya.validation.constraint;

import com.firatkaya.validation.validator.ValidateGoogleCaptcha;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ValidateGoogleCaptcha.class)
@Documented
public @interface ValidateCaptcha {

    String message() default "Sorry, this recaptcha is invalid. Please try again.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}