package com.digitalfutures.application;

import com.digitalfutures.app.AddressBook;
import com.digitalfutures.app.Contact;
import com.digitalfutures.app.UserInterface;
import com.digitalfutures.app.Util.ConsoleIO;

public class App {
    public static void main(String[] args) {
        ConsoleIO consoleIO = new ConsoleIO();
        AddressBook addressBook = new AddressBook();
        UserInterface userInterface = new UserInterface(consoleIO, addressBook);
        Contact contact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
        Contact contact2 = new Contact("Jesse Pinkman", "07111111111", "jpemail@hotmail.co.uk");
        Contact contact3 = new Contact("Skyler White", "07222222222", "newmail@gmail.com");
        try {
            addressBook.addContact(contact1);
            addressBook.addContact(contact2);
            addressBook.addContact(contact3);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        userInterface.start();
    }
}
