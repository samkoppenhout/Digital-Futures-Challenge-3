package com.digitalfutures.app;

import com.digitalfutures.app.Util.DuplicateException;

import java.util.ArrayList;

public class AddressBook {
    private final ArrayList<Contact> contactList = new ArrayList<>();

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    // For each contact in the contact list, run the search query
    public ArrayList<Contact> search(String searchTerm) {
        ArrayList<Contact> searchResults = new ArrayList<>();
        for (Contact contact : contactList) {searchQuery(contact, searchTerm, searchResults);}
        return searchResults;
    }

    // Try to add a contact to the contactList array, calling the duplicate checker in the process
    public void addContact(Contact contact) throws DuplicateException{
        this.contactList.add(duplicateCheckLoop(contact));
    }

    // If the contact is present, remove it, otherwise throw an exception
    public void removeContact(Contact contact) {
        if (this.contactList.contains(contact)) {
            this.contactList.remove(contact);
        } else throw new IllegalArgumentException("Contact not in address book.");
    }

    // Set the name, phone number and email of the provided contact
    public void editContact(Contact contact, String name, String phoneNumber, String email) {
        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmail(email);
    }

    // For each contact in the contact list, calls the duplicate check function
    private Contact duplicateCheckLoop(Contact newContact) throws DuplicateException {
        for (Contact contact :contactList) {
            duplicateCheck(contact, newContact);
        }
        return newContact;
    }

    // If the phone number or email matches between the given contact and the new contact, an error is thrown
    private void duplicateCheck(Contact contact, Contact newContact) throws DuplicateException {
        if ( contact.getEmail().equals(newContact.getEmail()) || contact.getPhoneNumber().equals(newContact.getPhoneNumber())) {
            throw new DuplicateException("Contact is already in address book.");
        }
    }

    // If the name contains the search term, both converted to lower case, add the contact to the search results
    private void searchQuery(Contact contact, String searchTerm, ArrayList<Contact> searchResults) {
            if (contact.getName().toLowerCase().contains(searchTerm.toLowerCase())) { searchResults.add(contact); }
    }
}
