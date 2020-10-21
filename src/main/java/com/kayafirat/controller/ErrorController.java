package com.kayafirat.controller;

import com.kayafirat.entity.Error;
import com.kayafirat.service.ErrorService;
import com.kayafirat.validation.constraint.ValidateCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/error")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ErrorController {

    private final ErrorService errorService;
    @GetMapping
    public ResponseEntity<?> getErrors(){
        return ResponseEntity.ok(errorService.getErrors());
    }
    @GetMapping(value = "/detail")
    public ResponseEntity<?> getError(@RequestParam Long id){
        return ResponseEntity.ok(errorService.getError(id));
    }
    @PostMapping
    public ResponseEntity<?> addError(@RequestBody Error error, @RequestParam @ValidateCaptcha String captcha)   {
        return ResponseEntity.ok(errorService.saveError(error));
    }
    @PutMapping
    public ResponseEntity<?> updateError(@RequestBody Error error){
        return ResponseEntity.ok(errorService.updateError(error));
    }
    @DeleteMapping
    public ResponseEntity<?> deleteError(@RequestParam Long id){
        errorService.deleteError(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping(value = "/status")
    public ResponseEntity<?> updateReadStatus(@RequestParam Long id){
        errorService.updateReadStatus(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
