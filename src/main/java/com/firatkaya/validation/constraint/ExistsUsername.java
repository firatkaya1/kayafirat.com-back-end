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
public @interface ExistsUsername {

    String message() default "Sorry, There is no record in this username.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
