package com.firatkaya.validation.constraint;


import com.firatkaya.validation.validator.ValidPostIdMapValidator;
import com.firatkaya.validation.validator.ValidPostIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= {ValidPostIdValidator.class, ValidPostIdMapValidator.class})
@Documented
public @interface ExistsPostId {

    String message() default "Sorry, there is not record in this post id.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
