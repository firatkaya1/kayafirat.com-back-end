package com.firatkaya.validation.validator;

import com.firatkaya.entity.User;
import com.firatkaya.exceptions.customExceptions.UserEmailNotFoundException;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.validation.constraint.ExistsEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidEmailUserValidator  implements ConstraintValidator<ExistsEmail, User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(ExistsEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (!userRepository.existsByUserEmail(user.getUserEmail())){
            throw new UserEmailNotFoundException(user.getUserEmail());
        }
        return true;
    }
}