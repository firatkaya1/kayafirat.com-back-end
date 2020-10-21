package com.kayafirat.validation.constraint;


import com.kayafirat.validation.validator.ValidCommentIdMapValidator;
import com.kayafirat.validation.validator.ValidCommentIdValidator;

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
