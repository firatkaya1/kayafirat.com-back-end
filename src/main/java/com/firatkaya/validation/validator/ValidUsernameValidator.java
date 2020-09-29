package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.UserNameNotFoundException;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.validation.constraint.ExistsUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidUsernameValidator implements ConstraintValidator<ExistsUsername,String> {

    @Autowired
    UserRepository userRepository;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!userRepository.existsByUserName(value)) {
            throw new UserNameNotFoundException(value);
        }
        return true;
    }
}
