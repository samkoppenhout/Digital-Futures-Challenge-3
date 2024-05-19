package com.digitalfutures.app;

import com.digitalfutures.app.Util.ConsoleIO;
import com.digitalfutures.app.Util.DataValidator;
import com.digitalfutures.app.Util.DuplicateException;

import java.util.ArrayList;

public class UserInterface {
    private final AddressBook addressBook;
    private final ConsoleIO consoleIO;

    public UserInterface(ConsoleIO consoleIO, AddressBook addressBook) {
        this.addressBook = addressBook;
        this.consoleIO = consoleIO;
    }

    // Calls the menu printer, then waits for a response and calls the corresponding function
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

    // Calls the print contact function for each contact in the list it is given, as long as it is not empty
    private ArrayList<Contact> printContacts(ArrayList<Contact> contacts) {
        if (contacts.isEmpty()) {
            consoleIO.print("No matching contacts found!\n");} else {
            int i = 1;
            for (Contact contact : contacts) {
                printContact(contact, i++);
            }
        }
        return contacts;
    }

    // Prints a string with the full details of a given contact with its numbered position in the list of contacts
    private void printContact(Contact contact, int i) {
        consoleIO.print(String.format("%d: Name: '%s', Phone Number: '%s', Email: '%s'\n",i, contact.getName(), contact.getPhoneNumber(), contact.getEmail()));
    }

    private ArrayList<Contact> search() {
        // Reads the input as the search term
        String search = consoleIO.takeInput();
        // Prints and returns the result of the address book's search function, using the input as an argument
        return printContacts(addressBook.search(search));
    }

    // Sends the full list of contacts to the printer function
    private void showAll() {
        printContacts(addressBook.getContactList());
    }

    private void add() {
        entry:
        while (true) {
            // Looping until all inputs are complete, calls the functions to add each detail for a new contact
            String name = AddEnterName();
            String phoneNumber = AddEnterPhoneNumber();
            String email = AddEnterEmail();
            confirm:
            while (true) {
                // Asks the user to confirm their entry
                consoleIO.print("Is the following entry correct?\n");
                consoleIO.print(String.format("Name: '%s', Phone Number: '%s', Email: '%s'\n", name, phoneNumber, email));
                consoleIO.print("1: Yes\n2: Retry\n3: Abort\n");
                String response = consoleIO.takeInput();
                switch (response.toLowerCase()) {
                    case "yes": case "1": case "y":
                        // If the user confirms, try to create an instance of the contact and add it into the address book
                        try {
                            Contact contact = new Contact(name, phoneNumber, email);
                            addressBook.addContact(contact);
                            consoleIO.print("Contact added successfully!\n");
                        } catch (DuplicateException exception) {
                            // If the contact is a duplicate, print the relevant error message and return to the main menu
                            consoleIO.print(exception.getMessage() + " Returning to menu.\n");
                            break entry;
                        } catch (Exception exception) {
                            // For any other issue, prompt the user to renter the details
                            consoleIO.print(exception.getMessage() + " Re-enter contact details.\n");
                            break confirm;
                        }
                        break entry;
                    case "retry": case "2": case "r": break; // Return to the start of the detail entry input
                    case "abort": case "3": case "a":
                        // Returns to the main menu
                        consoleIO.print("Contact not added. Returning to menu!\n");
                        break entry;
                    default:
                        // Flag that the input is not recognised and ask the same question
                        consoleIO.print("Input not recognised!\n");
                }
            }
        }
    }

    // Adds name if it passes the validator check, otherwise prints the error message
    private String AddEnterName() {
        while (true) {
            consoleIO.print("Enter name:\n");
            String name = consoleIO.takeInput();
            try {
                return DataValidator.checkName(name);
            } catch (Exception exception) { consoleIO.print(exception.getMessage() + "Re-enter name:\n");}
        }
    }

    // Adds phone number if it passes the validator check, otherwise prints the error message
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

    // Adds email if it passes the validator check, otherwise prints the error message
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

    private void remove() {
        ArrayList<Contact> results = search(); // Prompts the user to search for a contact to remove
        if (results.isEmpty()) { return; } // If the results are empty, do nothing
        while (true) {
            // Prompt the user to choose contact from the search list
            consoleIO.print("Choose the number next to the contact to remove, or type 'ABORT' to return to the main menu:\n");
            String option = consoleIO.takeInput();
            if (option.equals("ABORT")) {
                // Allows the user to not remove a contact at this stage
                consoleIO.print("No contacts removed. Returning to main menu!\n");
                return;
            }
            try {
                // Tries to remove the given contact
                int optionNo = Integer.parseInt(option);
                Contact result = results.get(optionNo - 1);
                addressBook.removeContact(result);
                consoleIO.print(String.format("Removed contact %d.\n", optionNo));
                return;
            } catch (Exception exception) {consoleIO.print("Contact not identified.\n");} // prints an error message if the input is not a relevant index
        }
    }


    private void edit() {
        ArrayList<Contact> results = search(); // Prompts the user to search for a contact to remove
        if (!results.isEmpty()) {
            while (true) {
                // Prompts the user to choose an input
                consoleIO.print("Choose the number next to the contact to edit, or type 'ABORT' to return to the main menu:\n");
                String option = consoleIO.takeInput();
                if (option.equals("ABORT\n")) { // Allows the user to exit without editing any contacts
                    consoleIO.print("No contacts changed. Returning to main menu!\n");
                    return;
                }
                try { // Tries editing the given contact, prints an error message for any exceptions
                    int optionNo = Integer.parseInt(option);
                    Contact result = results.get(optionNo - 1);
                    editContact(result);
                    return;
                } catch (Exception exception) {consoleIO.print("Contact not identified.\n");}
            }
        }
    }

    private void editName(Contact contact) {
        while (true) { // Prompts the user to enter a new name
            consoleIO.print(String.format("Current name: %s\n", contact.getName()));
            consoleIO.print("Enter new name. Leave blank to leave the current name.\n");
            String name = consoleIO.takeInput();
            if (name.isEmpty()) {break;} // Leaves name unchanged if the input is blank
            try { // Tries to update the name and prints an error message if there are any issues
                contact.setName(DataValidator.checkName(name));
                break;
            } catch (Exception exception) {consoleIO.print(exception.getMessage() + "Re-enter name:\n");}
        }
    }

    private void editPhoneNumber(Contact contact) {
        while (true) { // Prompts the user to enter a new phone number
            consoleIO.print(String.format("Current phone number: %s\n", contact.getPhoneNumber()));
            consoleIO.print("Enter new phone number. Leave blank to leave the current phone number.\n");
            String phoneNumber = consoleIO.takeInput();
            if (phoneNumber.isEmpty()) {break;} // Leaves phone number unchanged if the input is blank
            try { // Tries to update the phone number and prints an error message if there are any issues
                contact.setPhoneNumber(DataValidator.checkPhoneNumber(phoneNumber));
                break;
            } catch (Exception exception) {consoleIO.print(exception.getMessage() + "Re-enter phone number:\n");}
        }
    }

    private void editEmail(Contact contact) {
        while (true) { // Prompts the user to enter a new email
            consoleIO.print(String.format("Current email: %s\n", contact.getEmail()));
            consoleIO.print("Enter new email. Leave blank to leave the current email.\n");
            String email = consoleIO.takeInput(); // Leaves email unchanged if the input is blank
            if (email.isEmpty()) {break;}
            try { // Tries to update the email and prints an error message if there are any issues
                contact.setEmail(DataValidator.checkEmail(email));
                break;
            } catch (Exception exception) {consoleIO.print(exception.getMessage() + "Re-enter email:\n");}
        }
    }

    private void editContact(Contact contact) {
        // Calls the functions to edit each detail one by one, then prints a success message
        editName(contact);
        editPhoneNumber(contact);
        editEmail(contact);
        consoleIO.print("Contact updated!\n");
    }

    private void mainMenuPrinter() {
        // Prints a list of options as the main menu.
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
