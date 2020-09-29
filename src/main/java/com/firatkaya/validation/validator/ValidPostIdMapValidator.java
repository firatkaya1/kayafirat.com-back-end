package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.PostNotFoundException;
import com.firatkaya.exceptions.customExceptions.UserEmailNotFoundException;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.validation.constraint.ExistsPostId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;

public class ValidPostIdMapValidator implements ConstraintValidator<ExistsPostId, HashMap<String,String>> {

    @Autowired
    PostRepository postRepository;

    @Override
    public void initialize(ExistsPostId constraintAnnotation) {
    }

    @Override
    public boolean isValid(HashMap<String, String> value, ConstraintValidatorContext context) {
        if(!postRepository.existsByPostId(value.get("postId"))){
            throw new PostNotFoundException(value.get("postId"));
        }
        return true;
    }
}
