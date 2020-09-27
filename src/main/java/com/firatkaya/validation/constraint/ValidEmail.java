package com.firatkaya.validation.constraint;


import com.firatkaya.validation.validator.ValidEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ValidEmailValidator.class)
@Documented
public @interface ValidEmail {

    Class<? extends Payload>[] payload() default {};

    String value();
}
