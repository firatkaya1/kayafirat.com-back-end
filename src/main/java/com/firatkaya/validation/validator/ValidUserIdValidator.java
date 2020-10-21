package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.UserIdNotFoundException;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.validation.constraint.ExistsId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidUserIdValidator implements ConstraintValidator<ExistsId,String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(ExistsId constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!userRepository.existsByUserId(value)) {
            throw new UserIdNotFoundException(value);
        }
        return true;
    }
}