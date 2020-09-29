package com.firatkaya.validation.constraint;


import com.firatkaya.validation.validator.ValidCommentIdMapValidator;
import com.firatkaya.validation.validator.ValidCommentIdValidator;
import com.firatkaya.validation.validator.ValidPostIdMapValidator;
import com.firatkaya.validation.validator.ValidPostIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= {ValidCommentIdValidator.class, ValidCommentIdMapValidator.class})
@Documented
public @interface ExistsCommentId {

    String message() default "Sorry, there is not record in this comment id.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
