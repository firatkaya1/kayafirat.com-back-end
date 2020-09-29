package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.UserEmailNotFoundException;
import com.firatkaya.exceptions.customExceptions.UserNameNotFoundException;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.validation.constraint.ExistsEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidEmailValidator implements ConstraintValidator<ExistsEmail,String>  {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(ExistsEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       if(!userRepository.existsByUserEmail(value)){
           throw new UserEmailNotFoundException(value);
       }
       return true;
    }
}
