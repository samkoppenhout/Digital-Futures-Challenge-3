package com.digitalfutures.app;

import com.digitalfutures.app.Util.DuplicateException;

import java.util.ArrayList;

public class AddressBook {
    private final ArrayList<Contact> contactList = new ArrayList<>();

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public ArrayList<Contact> search(String searchTerm) {
        ArrayList<Contact> search = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getName().toLowerCase().contains(searchTerm.toLowerCase())) { search.add(contact); }
        }
        return search;
    }

    private Contact duplicateCheckLoop(Contact newContact) throws DuplicateException {
        for (Contact contact :contactList) {
            if (duplicateCheck(contact, newContact)) {throw new DuplicateException("Contact is already in address book.");}
        }
        return newContact;
    }

    private boolean duplicateCheck(Contact contact, Contact newContact) {
        return contact.getEmail().equals(newContact.getEmail()) || contact.getPhoneNumber().equals(newContact.getPhoneNumber());
    }

    public void addContact(Contact contact) throws DuplicateException{
        this.contactList.add(duplicateCheckLoop(contact));
    }

    public void removeContact(Contact contact) {
        if (this.contactList.contains(contact)) {
            this.contactList.remove(contact);
        } else throw new IllegalArgumentException("Contact not in address book.");
    }

    public void editContact(Contact contact, String name, String phoneNumber, String email) {
        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmail(email);
    }
}
