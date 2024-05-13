package com.digitalfutures.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;


public class ContactTest {
    @Nested
    @DisplayName("Contact Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Name is set by constructor")
        void testNameSetByConstructor() {
            // Arrange
            String testName = "Test Person";
            Contact testContact = new Contact(testName, "email@email.com", "07000000000");

            // Act
            String actualName = testContact.getName();

            // Assert
            assertEquals(testName, actualName);
        }

        @Test
        @DisplayName("Phone number is set by constructor")
        void testPhoneNumberSetByConstructor() {
            // Arrange
            String testPhoneNumber = "07000000000";
            Contact testContact = new Contact("Test Person", testPhoneNumber, "email@email.com");

            // Act
            String actualPhoneNumber = testContact.getPhoneNumber();

            // Assert
            assertEquals(testPhoneNumber, actualPhoneNumber);
        }

        @Test
        @DisplayName("Email is set by constructor")
        void testEmailSetByConstructor() {
            // Arrange
            String testEmail = "email@email.com";
            Contact testContact = new Contact("Test Person", "07000000000", testEmail);

            // Act
            String actualEmail = testContact.getEmail();

            // Assert
            assertEquals(testEmail, actualEmail);
        }
    }
}
