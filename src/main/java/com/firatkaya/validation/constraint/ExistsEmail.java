package com.firatkaya.validation.constraint;


import com.firatkaya.validation.validator.ValidEmailMapValidator;
import com.firatkaya.validation.validator.ValidEmailUserValidator;
import com.firatkaya.validation.validator.ValidEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= {ValidEmailValidator.class, ValidEmailMapValidator.class, ValidEmailUserValidator.class})
@Documented
public @interface ExistsEmail {

    String message() default "Sorry, There is no record in this email.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
