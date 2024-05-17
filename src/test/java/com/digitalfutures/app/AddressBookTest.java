package com.digitalfutures.app;

import com.digitalfutures.app.Util.DuplicateException;
import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class AddressBookTest {

    String validName = "Test Name";
    String validPhoneNumber = "07000000000";
    String validEmail = "email@email.com";

    @Nested
    @DisplayName("Add Contact Tests")
    class addContactTests {
        private final AddressBook addressBook = new AddressBook();

        @Test
        @DisplayName("Number of contacts increases when addContact is called")
        void testNumberOfContactsIncreases() {
            // Arrange
            Contact testContact = new Contact(validName, validPhoneNumber, validEmail);
            int expected = addressBook.getContactList().size() + 1;
            // Act
            try {
                addressBook.addContact(testContact);
            } catch (DuplicateException e) {}
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
            try {
                addressBook.addContact(testContact);
            } catch (DuplicateException e) {}
            // Assert
            assertEquals(expected, addressBook.getContactList().size());
        }
    }

    @Nested
    @DisplayName("Remove Contact Tests")
    class removeContactTests {
        private final AddressBook addressBook = new AddressBook();

        @Test
        @DisplayName("Number of contacts decreases when removeContact is called")
        void testNumberOfContactsDecreases() {
            // Arrange
            Contact testContact = new Contact(validName, validPhoneNumber, validEmail);
            try {
                addressBook.addContact(testContact);
            } catch (DuplicateException e) {}
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
            try {
                addressBook.addContact(testContact);
            } catch (DuplicateException e) {}
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

    @Nested
    @DisplayName("Search By Name Tests")
    class searchByNameTests {
        private static AddressBook addressBook = new AddressBook();
        private static final Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
        private static final Contact testContact2 = new Contact("Jesse Pinkman", "07111111111", "jpemail@hotmail.co.uk");
        private static final Contact testContact3 = new Contact("Skyler White", "07222222222", "newmail@gmail.com");

        @BeforeEach
        void beforeEach() {
            addressBook = new AddressBook();
            try {
                addressBook.addContact(testContact1);
                addressBook.addContact(testContact2);
                addressBook.addContact(testContact3);
            } catch (DuplicateException e) {}
            }

        @Test
        @DisplayName("Check that the returned list of contacts includes a contact with the requested term")
        void testContactIsReturned() {
            // Arrange
            // Act
            // Assert
            assertTrue(addressBook.search("Walter").contains(testContact1));
        }

        @Test
        @DisplayName("Check the search result is empty if the term does not match any statements")
        void searchEmptyOnNoResults() {
            // Arrange
            // Act
            // Assert
            assertTrue(addressBook.search("Hank").isEmpty());
        }

        @Test
        @DisplayName("Check that the returned list of contacts includes a contact with the requested term if cases are different")
        void testContactIsReturnedCaseInsensitive() {
            // Arrange
            // Act
            // Assert
            assertTrue(addressBook.search("walter").contains(testContact1));
        }

        @Test
        @DisplayName("Check that the returned list of contacts is size 2 if 2 results are expected")
        void testTwoContactsAreReturned() {
            // Arrange
            int expected = 2;
            // Act
            // Assert
            assertEquals(expected, addressBook.search("white").size());
        }
    }

    @Nested
    @DisplayName("Edit Contact Tests")
    class editContactTests {
        private static AddressBook addressBook = new AddressBook();
        private static final Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
        private static final Contact testContact2 = new Contact("Jesse Pinkman", "07111111111", "jpemail@hotmail.co.uk");
        private static final Contact testContact3 = new Contact("Skyler White", "07222222222", "newmail@gmail.com");

        @BeforeAll
        static void beforeAll() {
            addressBook = new AddressBook();
            try {
                addressBook.addContact(testContact1);
                addressBook.addContact(testContact2);
                addressBook.addContact(testContact3);
            } catch (DuplicateException e) {}
        }

        @Test
        @DisplayName("Check that the returned list of contacts does not include the original contact after editing")
        void testEditChangesContact() {
            // Arrange
            // Act
            addressBook.editContact(testContact1, "Hank Schrader", "07333333333", "hank@police.com");
            // Assert
            assertTrue(addressBook.search("Walter White").isEmpty());
        }

        @Test
        @DisplayName("Check that the returned list of contacts does include the new contact after editing")
        void testEditAddsNewContact() {
            // Arrange
            int expected = 1;
            // Act
            addressBook.editContact(testContact1, "Hank Schrader", "07333333333", "hank@police.com");
            // Assert
            assertEquals(expected, addressBook.search("Hank Schrader").size());
        }
    }
    @Nested
    @DisplayName("Duplicate Tests")
    class duplicateTests {
        private static AddressBook addressBook = new AddressBook();

        @BeforeAll
        static void beforeAll() {
            addressBook = new AddressBook();
        }

        @Test
        @DisplayName("Check that an error is thrown if a duplicate is added")
        void testDuplicateCausesError() {
            // Arrange
            addressBook = new AddressBook();
            Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
            Contact testContact2 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
            try {
                addressBook.addContact(testContact1);
            } catch (DuplicateException e) {}
            // Act
            // Assert
            assertThrows(DuplicateException.class, () -> addressBook.addContact(testContact2));
        }

        @Test
        @DisplayName("Check that an error is thrown if a duplicate phone number is added with different email and name")
        void testPhoneNumberDuplicateCausesError() {
            // Arrange
            addressBook = new AddressBook();
            Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
            Contact testContact2 = new Contact("Hank Schrader", "07000000000", "hank@police.com");
            try {
                addressBook.addContact(testContact1);
            } catch (DuplicateException e) {}
            // Act
            // Assert
            assertThrows(DuplicateException.class, () -> addressBook.addContact(testContact2));
        }

        @Test
        @DisplayName("Check that an error is thrown if a duplicate email is added with different phone number and name")
        void testEmailDuplicateCausesError() {
            // Arrange
            addressBook = new AddressBook();
            Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
            Contact testContact2 = new Contact("Hank Schrader", "07111111111", "walterwhite@email.com");
            try {
                addressBook.addContact(testContact1);
            } catch (DuplicateException e) {}
            // Act
            // Assert
            assertThrows(DuplicateException.class, () -> addressBook.addContact(testContact2));
        }

        @Test
        @DisplayName("Check that an error is not thrown if a duplicate name is added with different phone number and email")
        void testNameDuplicateDoesNotCauseError() {
            // Arrange
            addressBook = new AddressBook();
            Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
            Contact testContact2 = new Contact("Walter White", "07111111111", "hank@police.com");
            try {
                addressBook.addContact(testContact1);
            } catch (DuplicateException e) {}
            // Act
            // Assert
            assertDoesNotThrow(() -> addressBook.addContact(testContact2));
        }

        @Test
        @DisplayName("Check that an error is not thrown if two entries are not duplicates")
        void testNonDuplicateDoesNotCauseError() {
            // Arrange
            addressBook = new AddressBook();
            Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
            Contact testContact2 = new Contact("Hank Schrader", "07111111111", "hank@police.com");
            try {
                addressBook.addContact(testContact1);
            } catch (DuplicateException e) {}
            // Act
            // Assert
            assertDoesNotThrow(() -> addressBook.addContact(testContact2));
        }
    }
    @Nested
    @DisplayName("Return All Tests")
    class returnAllTests {
        private static AddressBook addressBook = new AddressBook();

        @Test
        @DisplayName("Check that the returned list of contacts is empty when no contacts are added")
        void testEmptyGetContact() {
            // Arrange
            addressBook = new AddressBook();
            int expected = 0;
            // Act
            // Assert
            assertEquals(expected, addressBook.getContactList().size());
        }

        @Test
        @DisplayName("Check that the returned list of contacts is size 1 when 1 contacts are added")
        void testOneGetContact() {
            // Arrange
            addressBook = new AddressBook();
            Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
            try {
                addressBook.addContact(testContact1);
            } catch (DuplicateException e) {}
            int expected = 1;
            // Act
            // Assert
            assertEquals(expected, addressBook.getContactList().size());
        }

        @Test
        @DisplayName("Check that the returned list of contacts is size 2 when 2 contacts are added")
        void testTwoGetContact() {
            // Arrange
            addressBook = new AddressBook();
            Contact testContact1 = new Contact("Walter White", "07000000000", "walterwhite@email.com");
            try {
                addressBook.addContact(testContact1);
            } catch (DuplicateException e) {}
            Contact testContact2 = new Contact("Hank Schrader", "07111111111", "hank@police.com");
            try {
                addressBook.addContact(testContact2);
            } catch (DuplicateException e) {}
            int expected = 2;
            // Act
            // Assert
            assertEquals(expected, addressBook.getContactList().size());
        }
    }
}

