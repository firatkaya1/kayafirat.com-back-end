package com.firatkaya.validation.constraint;


import com.firatkaya.validation.validator.ValidEmailValidator;
import com.firatkaya.validation.validator.ValidUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ValidUsernameValidator.class)
@Documented
public @interface ValidUsername {

    Class<? extends Payload>[] payload() default {};

    String value();
}
