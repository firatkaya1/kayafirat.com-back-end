package com.kayafirat.service.Impl;

import com.kayafirat.entity.Contact;
import com.kayafirat.repository.ContactRepository;
import com.kayafirat.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void updateReadStatus(Long id){
        contactRepository.updateReadStatus(id);
    }
}
