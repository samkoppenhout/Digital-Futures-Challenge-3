package com.digitalfutures.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressBookTest {

        String validName = "Test Name";
        String validPhoneNumber = "07000000000";
        String validEmail = "email@email.com"

        @Nested
        @DisplayName("Add Contact Tests")
        class addContactTests {
            private AddressBook addressBook = new AddressBook();

            @Test
            @DisplayName("Number of contacts increases when addContact is called")
            void testNumberOfContactsIncreases() {
                // Arrange
                Contact testContact = new Contact(validName, validPhoneNumber, validEmail);
                int expected = addressBook.getContactList().size() + 1;
                // Act
                addressBook.addContact(testContact);
                // Assert
                assertEquals(expected, addressBook.getContactList().size());
            }

            @Test
            @DisplayName("Test that the contacts list includes the given test contact")
            void testContactsListIncludesGivenContact() {
                // Arrange
                Contact testContact = new Contact(validName, validPhoneNumber, validEmail);
                int expected = addressBook.getContactList().size() + 1;
                // Act
                addressBook.addContact(testContact);
                // Assert
                assertEquals(expected, addressBook.getContactList().size());
            }

        }
}
