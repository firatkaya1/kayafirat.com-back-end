package com.kayafirat.controller;

import com.kayafirat.entity.Contact;
import com.kayafirat.service.ContactService;
import com.kayafirat.validation.constraint.ValidateCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/v1/contact")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<?> getContacts(){
        return ResponseEntity.ok(contactService.getContacts());
    }
    @GetMapping(value = "/detail")
    public ResponseEntity<?> getContact(@RequestParam Long id){
        return ResponseEntity.ok(contactService.getContact(id));
    }
    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody Contact contact,@RequestParam @ValidateCaptcha String captcha){
        return ResponseEntity.ok(contactService.saveContact(contact));
    }
    @PutMapping
    public ResponseEntity<?> updateContact(@RequestBody Contact contact){
        return ResponseEntity.ok(contactService.updateContact(contact));
    }
    @DeleteMapping
    public ResponseEntity<?> deleteContact(@RequestParam Long id){
        contactService.deleteContact(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/status")
    public ResponseEntity<?> updateReadStatus(@RequestParam Long id){
        contactService.updateReadStatus(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
