package com.kayafirat.validation.validator;

import com.kayafirat.exceptions.customExceptions.UserNameNotFoundException;
import com.kayafirat.repository.UserRepository;
import com.kayafirat.validation.constraint.ExistsUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidUsernameValidator implements ConstraintValidator<ExistsUsername,String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(ExistsUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!userRepository.existsByUserName(value)) {
            throw new UserNameNotFoundException(value);
        }
        return true;
    }
}
