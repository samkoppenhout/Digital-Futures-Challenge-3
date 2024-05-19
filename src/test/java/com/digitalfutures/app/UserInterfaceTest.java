package com.digitalfutures.app;
import com.digitalfutures.app.Util.ConsoleIO;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserInterfaceTest {

    private UserInterface userInterface;
    private ConsoleIO mockConsoleIO;
    private AtomicInteger currentIndex;

    // Sets up the ConsoleIO.takeInput function to input the given inputs in order
    private void inputSetup(String... inputs) {
        when(mockConsoleIO.takeInput()).thenAnswer(eachCall -> {
            int index = currentIndex.getAndIncrement();
            return inputs[Math.min(index, inputs.length - 1)];
        });
    }

    // Checks if any of the outputs of the program contain the expected result
    private boolean verifyContains(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockConsoleIO, atLeastOnce()).print(captor.capture());
        List<String> capturedValues = captor.getAllValues();
        return capturedValues.stream().anyMatch(value -> value.contains(expected));
    }

    @BeforeEach
    public void setUp() {
        mockConsoleIO = mock(ConsoleIO.class);
        AddressBook addressBook = new AddressBook();
        userInterface = new UserInterface(mockConsoleIO, addressBook);
        currentIndex = new AtomicInteger(0);
    }

    @Nested
    @DisplayName("UI Tests")
    class UserInterfaceTests {
        @Test
        @DisplayName("Check that the menu is printed on startup, and closes on an input of 6")
        public void testMenu() {
            // Arrange
            inputSetup("6");
            String expected = "Main Menu:\n";

            // Act
            userInterface.start();

            // Assert
            assertTrue(verifyContains(expected));
        }

        @Nested
        @DisplayName("Adding a contact")
        class addContactTests {
            @Test
            @DisplayName("Check that a contact is added successfully when the details are correct")
            public void testCorrectDetailsAdd() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1", "6");
                String expected = "Contact added successfully!\n";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }

            @Test
            @DisplayName("Check that an email error message is sent if an incorrect email is entered")
            public void testIncorrectEmailAdd() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam", "sam@sam.com","1", "6");
                String expected = "Email invalid. Re-enter email:\n";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }

            @Test
            @DisplayName("Check that a phone number error message is sent if an incorrect phone number is entered")
            public void testIncorrectPhoneNumberAdd() {
                // Arrange
                inputSetup("3", "Sam K", "", "07999999999", "sam@sam.com","1", "6");
                String expected = "Phone number invalid. Re-enter phone number:\n";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }

            @Test
            @DisplayName("Check that a duplicate contact cannot be added")
            public void testDuplicateAdd() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1", "3", "Sam K", "07999999999", "sam@sam.com","1", "6");
                String expected = "Contact is already in address book. Returning to menu.\n";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }
        }

        @Nested
        @DisplayName("Searching by name")
        class searchByNameTests {
            @Test
            @DisplayName("Test that a contact which has been added can be found in a search")
            public void testContactAddedSearch() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1", "1", "Sam K", "6");
                String expected = "Name: 'Sam K', Phone Number: '07999999999', Email: 'sam@sam.com'";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }

            @Test
            @DisplayName("Test that the search only shows relevant results")
            public void testSearchRelevance() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1", "3", "Ed W", "07888888888", "ed@ed.com", "1", "1", "Sam K", "6");
                String expected = ": Name: 'Ed W', Phone Number: '07888888888', Email: 'ed@ed.com'";

                // Act
                userInterface.start();

                // Assert
                assertFalse(verifyContains(expected));
            }
        }

        @Nested
        @DisplayName("Showing all contacts")
        class showAllContactTests {
            @Test
            @DisplayName("Test that option 2 starts show all contacts")
            public void test2StartsShowAllContacts() {
                // Arrange
                inputSetup("2", "6");
                String expected = "Showing all contacts:\n";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }

            @Test
            @DisplayName("Test that option 2 shows a contact when one is added")
            public void testRemoveNotAdded() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1", "2", "6");
                String expected = ": Name: 'Sam K', Phone Number: '07999999999', Email: 'sam@sam.com'";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }

            @Test
            @DisplayName("Test that option 2 shows both contacts when two are added")
            public void testShowAllTwoContacts() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1","3", "Ed W", "07888888888", "ed@ed.com", "1", "2", "6");
                String expected = ": Name: 'Sam K', Phone Number: '07999999999', Email: 'sam@sam.com'";
                String expected2 = ": Name: 'Ed W', Phone Number: '07888888888', Email: 'ed@ed.com'";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected)&&verifyContains(expected2));
            }

            @Test
            @DisplayName("Test that option 2 shows both contacts in order when two are added")
            public void testShowAllTwoContactsInOrder() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1","3", "Ed W", "07888888888", "ed@ed.com", "1", "2", "6");
                String expected = "1: Name: 'Sam K', Phone Number: '07999999999', Email: 'sam@sam.com'";
                String expected2 = "2: Name: 'Ed W', Phone Number: '07888888888', Email: 'ed@ed.com'";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected)&&verifyContains(expected2));
            }
        }

        @Nested
        @DisplayName("Removing a contact")
        class removeContactTests {
            @Test
            @DisplayName("Test that a contact which has been added can be removed")
            public void testRemoveAdded() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1", "4", "Sam K", "1", "6");
                String expected = "Removed contact 1.\n";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }

            @Test
            @DisplayName("Test that a contact which does not exist cannot be removed")
            public void testRemoveNotAdded() {
                // Arrange
                inputSetup("4", "Sam K", "6");
                String expected = "No matching contacts found!\n";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }
        }

        @Nested
        @DisplayName("Edit a contact")
        class editContactTests {
            @Test
            @DisplayName("Test that a contact which has been added can be edited")
            public void testEdit() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@sam.com", "1", "5", "Sam K", "1", "", "", "sam@email.com", "2", "6");
                String expected = ": Name: 'Sam K', Phone Number: '07999999999', Email: 'sam@email.com'";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }

            @Test
            @DisplayName("Test that invalid data is not accepted")
            public void testRemoveAdded() {
                // Arrange
                inputSetup("3", "Sam K", "07999999999", "sam@email.com", "1", "5", "Sam K", "1", "", "", "sam@email", "", "1", "2", "6");
                String expected = ": Name: 'Sam K', Phone Number: '07999999999', Email: 'sam@email.com'";

                // Act
                userInterface.start();

                // Assert
                assertTrue(verifyContains(expected));
            }
        }
    }
}