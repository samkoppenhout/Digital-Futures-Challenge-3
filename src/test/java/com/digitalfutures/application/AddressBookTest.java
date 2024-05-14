package com.digitalfutures.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressBookTest {

        String validName = "Test Name";
        String validPhoneNumber = "07000000000";
        String validEmail = "email@email.com";

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

    @Nested
    @DisplayName("Remove Contact Tests")
    class removeContactTests {
        private AddressBook addressBook = new AddressBook();

        @Test
        @DisplayName("Number of contacts decreases when removeContact is called")
        void testNumberOfContactsDecreases() {
            // Arrange
            Contact testContact = new Contact(validName, validPhoneNumber, validEmail);
            addressBook.addContact(testContact);
            int expected = addressBook.getContactList().size() - 1;
            // Act
            addressBook.removeContact(testContact);
            // Assert
            assertEquals(expected, addressBook.getContactList().size());
        }

        @Test
        @DisplayName("Test that the contacts list does not include the given test contact")
        void testContactsListDoesNotIncludeGivenContact() {
            // Arrange
            Contact testContact = new Contact(validName, validPhoneNumber, validEmail);
            addressBook.addContact(testContact);
            // Act
            addressBook.removeContact(testContact);
            // Assert
            assertFalse(addressBook.getContactList().contains(testContact));
        }

        @Test
        @DisplayName("removeContact() throws an error if contacts list does not contain the given contact")
        void cannotRemoveContactThatIsNotPresent() {
            // Arrange
            Contact testContact = new Contact(validName, validPhoneNumber, validEmail);
            // Act
            // Assert
            assertThrows(IllegalArgumentException.class, () -> addressBook.removeContact(testContact));
        }
    }
}

