package com.firatkaya.service;

import com.firatkaya.entity.Contact;

import java.util.List;

public interface ContactService {

    Contact saveContact(Contact contact);

    Contact updateContact(Contact contact);

    void deleteContact(Long id);

    void deleteContacts();

    Contact getContact(Long id);

    List<Contact> getContacts();

}
