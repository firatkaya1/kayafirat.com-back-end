package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.CommentNotFoundException;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.validation.constraint.ExistsCommentId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCommentIdValidator implements ConstraintValidator<ExistsCommentId, String> {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public void initialize(ExistsCommentId constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!commentRepository.existsById(value)){
            throw new CommentNotFoundException(value);
        }
        return true;
    }
}
