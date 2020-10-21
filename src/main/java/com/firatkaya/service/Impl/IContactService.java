package com.firatkaya.service.Impl;

import com.firatkaya.entity.Contact;
import com.firatkaya.repository.ContactRepository;
import com.firatkaya.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IContactService implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(Long id) {
         contactRepository.deleteById(id);
    }

    @Override
    public void deleteContacts() {
        contactRepository.deleteAll();
    }

    @Override
    public Contact getContact(Long id) {
        return contactRepository.findByContactId(id);
    }

    @Override
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact updateReadStatus(Long id){
        return contactRepository.updateReadStatus(id);
    }
}
