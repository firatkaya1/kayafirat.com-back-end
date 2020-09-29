package com.firatkaya.validation.validator;

import com.firatkaya.entity.User;
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
        userRepository.existsByUserEmail(user.getUserEmail());
        return true;
    }
}