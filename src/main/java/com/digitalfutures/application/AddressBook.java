package com.digitalfutures.application;

import java.util.ArrayList;

public class AddressBook {
    ArrayList<Contact> contactList = new ArrayList<>();

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void addContact(Contact contact) {
        this.contactList.add(contact);
    }
}
