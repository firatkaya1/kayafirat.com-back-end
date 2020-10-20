package com.firatkaya.validation.validator;

import com.firatkaya.exceptions.customExceptions.GoogleCaptchaNotValidException;
import com.firatkaya.exceptions.customExceptions.PostNotFoundException;
import com.firatkaya.validation.constraint.ValidateCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateGoogleCaptcha implements ConstraintValidator<ValidateCaptcha, String> {

    @Autowired
    private Environment env;

    private final RestTemplate restTemplate;

    @Autowired
    public ValidateGoogleCaptcha(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public void initialize(ValidateCaptcha constraintAnnotation) {

    }

    @Override
    public boolean isValid(String key, ConstraintValidatorContext context) {
        String url = env.getProperty("google.recaptcha.verify-link") + "secret=" + env.getProperty("google.recaptcha.secret-key") + "&response=" + key;
        String response =restTemplate.getForObject(url, String.class);
        boolean isValidCaptcha =Boolean.parseBoolean(response.substring(response.indexOf("\":")+3,response.indexOf(","))) ;
        if (!isValidCaptcha){
            throw  new GoogleCaptchaNotValidException(context.getDefaultConstraintMessageTemplate());
        }
        return true;
    }
}
