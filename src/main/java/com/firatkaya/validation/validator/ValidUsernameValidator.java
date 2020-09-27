package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.UserEmailNotFoundException;
import com.firatkaya.exceptions.UserNameNotFoundException;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.validation.constraint.ValidEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidUsernameValidator implements ConstraintValidator<ValidEmail,String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!userRepository.existsByUserName(value)) {
            throw new UserNameNotFoundException(value);
        }
        return true;
    }
}
