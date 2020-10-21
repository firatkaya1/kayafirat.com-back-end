package com.kayafirat.validation.constraint;


import com.kayafirat.validation.validator.ValidUserIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ValidUserIdValidator.class)
@Documented
public @interface ExistsId {

    String message() default "Sorry, There is no record in this id.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
