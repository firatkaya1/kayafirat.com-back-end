package com.firatkaya.validation.constraint;


import com.firatkaya.validation.validator.ValidEmailValidator;
import com.firatkaya.validation.validator.ValidUserIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ValidUserIdValidator.class)
@Documented
public @interface ExistsId {

    String message() default "Sorry, passwords does not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
