package com.kayafirat.validation.validator;

import com.kayafirat.exceptions.customExceptions.UserEmailNotFoundException;
import com.kayafirat.repository.UserRepository;
import com.kayafirat.validation.constraint.ExistsEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;

public class ValidEmailMapValidator implements ConstraintValidator<ExistsEmail, HashMap<String,String>> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(ExistsEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(HashMap<String, String> value, ConstraintValidatorContext context) {
        if(!userRepository.existsByUserEmail(value.get("email"))){
            throw new UserEmailNotFoundException(value.get("email"));
        }
        return true;
    }
}
