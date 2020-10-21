package com.kayafirat.validation.constraint;


import com.kayafirat.validation.validator.ValidEmailMapValidator;
import com.kayafirat.validation.validator.ValidEmailUserValidator;
import com.kayafirat.validation.validator.ValidEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= {ValidEmailValidator.class, ValidEmailMapValidator.class, ValidEmailUserValidator.class})
@Documented
public @interface ExistsEmail {

    String message() default "Sorry, There is no record in this email.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
