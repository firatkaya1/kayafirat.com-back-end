package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.PostNotFoundException;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.validation.constraint.ExistsPostId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPostIdValidator implements ConstraintValidator<ExistsPostId,String> {

    @Autowired
    PostRepository postRepository;

    @Override
    public void initialize(ExistsPostId constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!postRepository.existsByPostId(value)) {
            throw new PostNotFoundException(value);
        }
        return true;
    }
}
