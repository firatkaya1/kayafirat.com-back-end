package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.CommentNotFoundException;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.validation.constraint.ExistsEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;

public class ValidCommentIdValidator implements ConstraintValidator<ExistsEmail, String> {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public void initialize(ExistsEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!commentRepository.existsById(value)){
            throw new CommentNotFoundException(value);
        }
        return true;
    }
}
