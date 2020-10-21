package com.firatkaya.controller;

import com.firatkaya.entity.Contact;
import com.firatkaya.service.ContactService;
import com.firatkaya.validation.constraint.ValidateCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/contact")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<?> getContacts(){
        return ResponseEntity.ok(contactService.getContacts());
    }
    @GetMapping
    public ResponseEntity<?> getContact(@RequestParam Long id){
        return ResponseEntity.ok(contactService.getContact(id));
    }
    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody Contact contact, @RequestParam @ValidateCaptcha String captcha)   {
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



}
