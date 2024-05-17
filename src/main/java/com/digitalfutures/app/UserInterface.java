package com.digitalfutures.app;

import com.digitalfutures.app.Util.ConsoleIO;
import com.digitalfutures.app.Util.DataValidator;
import com.digitalfutures.app.Util.DuplicateException;

import java.util.ArrayList;

public class UserInterface {
    private final AddressBook addressBook;
    private final ConsoleIO consoleIO;

    private ArrayList<Contact> printContacts(ArrayList<Contact> contacts) {
        if (contacts.isEmpty()) {
            consoleIO.print("No matching contacts found!\n");} else {
            int i = 1;
            for (Contact contact : contacts) { consoleIO.print(String.format("%d: Name: '%s', Phone Number: '%s', Email: '%s'\n",i++, contact.getName(), contact.getPhoneNumber(), contact.getEmail()));}
        }
        return contacts;
    }

    private ArrayList<Contact> search() {
        consoleIO.print("Showing all contacts:\n");
        String search = consoleIO.takeInput();
        return printContacts(addressBook.search(search));
    }

    private void showAll() {
        printContacts(addressBook.getContactList());
    }

    private String AddEnterName() {
        while (true) {
            consoleIO.print("Enter name:\n");
            String name = consoleIO.takeInput();
            try {
                DataValidator.checkName(name);
                return name;
            } catch (Exception exception) { consoleIO.print(exception.getMessage() + "Re-enter name:\n");}
        }
    }

    private String AddEnterPhoneNumber() {
        while (true) {
            consoleIO.print("Enter phone number:\n");
            String phoneNumber = consoleIO.takeInput();
            try {
                DataValidator.checkPhoneNumber(phoneNumber);
                return phoneNumber;
            } catch (Exception exception) { consoleIO.print(exception.getMessage() + "Re-enter phone number:\n");}
        }
    }

    private String AddEnterEmail() {
        while (true) {
            consoleIO.print("Enter email:\n");
            String email = consoleIO.takeInput();
            try {
                DataValidator.checkEmail(email);
                return email;
            } catch (Exception exception) { consoleIO.print(exception.getMessage() + "Re-enter email:\n");}
        }
    }

    private void add() {
        entry:
        while (true) {
            String name = AddEnterName();
            String phoneNumber = AddEnterPhoneNumber();
            String email = AddEnterEmail();
            confirm:
            while (true) {
                consoleIO.print("Is the following entry correct?\n");
                consoleIO.print(String.format("Name: '%s', Phone Number: '%s', Email: '%s'\n", name, phoneNumber, email));
                consoleIO.print("1: Yes\n2: Retry\n3: Abort\n");
                String response = consoleIO.takeInput();
                switch (response.toLowerCase()) {
                    case "yes": case "1": case "y":
                        try {
                            Contact contact = new Contact(name, phoneNumber, email);
                            addressBook.addContact(contact);
                            consoleIO.print("Contact added successfully!\n");
                        } catch (DuplicateException exception) {
                            consoleIO.print(exception.getMessage() + " Returning to menu.\n");
                            break entry;
                        } catch (Exception exception) {
                            consoleIO.print(exception.getMessage() + " Re-enter contact details.\n");
                            break confirm;
                        }
                        break entry;
                    case "retry": case "2": case "r": break;
                    case "abort": case "3": case "a":
                        consoleIO.print("Contact not added. Returning to menu!\n");
                        break entry;
                    default:
                        consoleIO.print("Input not recognised!\n");
                        break confirm;
                }
            }
        }
    }

    private void remove() {
        ArrayList<Contact> results = search();
        if (!results.isEmpty()) {
            while (true) {
                consoleIO.print("Choose the number next to the contact to remove, or type 'ABORT' to return to the main menu:\n");
                String option = consoleIO.takeInput();
                if (option.equals("ABORT")) {
                    consoleIO.print("No contacts removed. Returning to main menu!\n");
                    return;
                }
                try {
                    int optionNo = Integer.parseInt(option);
                    Contact result = results.get(optionNo - 1);
                    addressBook.removeContact(result);
                    consoleIO.print(String.format("Removed contact %d.\n", optionNo));
                    return;
                } catch (Exception exception) {consoleIO.print("Contact not identified.\n");}
            }
        }
    }

    private void edit() {
        ArrayList<Contact> results = search();
        if (!results.isEmpty()) {
            while (true) {
                consoleIO.print("Choose the number next to the contact to edit, or type 'ABORT' to return to the main menu:\n");
                String option = consoleIO.takeInput();
                if (option.equals("ABORT\n")) {
                    consoleIO.print("No contacts changed. Returning to main menu!\n");
                    return;
                }
                try {
                    int optionNo = Integer.parseInt(option);
                    Contact result = results.get(optionNo - 1);
                    editContact(result);
                    return;
                } catch (Exception exception) {consoleIO.print("Contact not identified.\n");}
            }
        }
    }

    private void editName(Contact contact) {
        while (true) {
            consoleIO.print(String.format("Current name: %s\n", contact.getName()));
            consoleIO.print("Enter new name. Leave blank to leave the current name.\n");
            String name = consoleIO.takeInput();
            if (name.isEmpty()) {break;}
            try {
                contact.setName(DataValidator.checkName(name));
                break;
            } catch (Exception exception) {consoleIO.print(exception.getMessage() + "Re-enter name:\n");}
        }
    }

    private void editPhoneNumber(Contact contact) {
        while (true) {
            consoleIO.print(String.format("Current phone number: %s\n", contact.getPhoneNumber()));
            consoleIO.print("Enter new phone number. Leave blank to leave the current phone number.\n");
            String phoneNumber = consoleIO.takeInput();
            if (phoneNumber.isEmpty()) {break;}
            try {
                contact.setPhoneNumber(DataValidator.checkPhoneNumber(phoneNumber));
                break;
            } catch (Exception exception) {consoleIO.print(exception.getMessage() + "Re-enter phone number:\n");}
        }
    }

    private void editEmail(Contact contact) {
        while (true) {
            consoleIO.print(String.format("Current email: %s\n", contact.getEmail()));
            consoleIO.print("Enter new email. Leave blank to leave the current email.\n");
            String email = consoleIO.takeInput();
            if (email.isEmpty()) {break;}
            try {
                contact.setEmail(DataValidator.checkEmail(email));
                break;
            } catch (Exception exception) {consoleIO.print(exception.getMessage() + "Re-enter email:\n");}
        }
    }

    private void editContact(Contact contact) {
        editName(contact);
        editPhoneNumber(contact);
        editEmail(contact);
        consoleIO.print("Contact updated!\n");
    }

    public UserInterface(ConsoleIO consoleIO, AddressBook addressBook) {
        this.addressBook = addressBook;
        this.consoleIO = consoleIO;
    }

    public void start() {
        while (true) {
            mainMenuPrinter();
            String option = consoleIO.takeInput();
            switch (option.toLowerCase()) {
                case "1": case "search":
                    consoleIO.print("Search:\n");
                    search();
                    break;
                case "2": case "show all":
                    consoleIO.print("Showing all contacts:\n");
                    showAll();
                    break;
                case "3": case "add":
                    consoleIO.print("Adding new contact:\n");
                    add();
                    break;
                case "4": case "remove":
                    consoleIO.print("Searching for contact to remove.\n");
                    remove();
                    break;
                case "5": case "edit":
                    consoleIO.print("Searching for contact to edit.\n");
                    edit();
                    break;
                case "6": case "exit":
                    consoleIO.print("Exiting program.\n");
                    return;
                default:
                    consoleIO.print("Input not recognised!\n");
                    break;
            }
        }
    }

    private void mainMenuPrinter() {
        consoleIO.print("Main Menu:\n");
        consoleIO.print("Enter the number of your chosen action!\n");
        consoleIO.print("1: Search for contacts\n");
        consoleIO.print("2: Show all contacts\n");
        consoleIO.print("3: Add a contact\n");
        consoleIO.print("4: Remove a contact\n");
        consoleIO.print("5: Edit contact\n");
        consoleIO.print("6: Exit program\n");
    }
}
