package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.CommentNotFoundException;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.validation.constraint.ExistsCommentId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;

public class ValidCommentIdMapValidator implements ConstraintValidator<ExistsCommentId, HashMap<String,String>> {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public void initialize(ExistsCommentId constraintAnnotation) {

    }

    @Override
    public boolean isValid(HashMap<String, String> value, ConstraintValidatorContext context) {
        if(!commentRepository.existsById(value.get("commentId"))){
            throw new CommentNotFoundException(value.get("commentId"));
        }
        return true;
    }
}
