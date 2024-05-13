package com.digitalfutures.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataValidatorTest {
    @Nested
    @DisplayName("Name Tests")
    class NameTests {
        @Test
        @DisplayName("Check that an error is not thrown if name is valid")
        void testValidName(){
            // Arrange
            String testName = "testName";
            // Act
            // Assert
            assertDoesNotThrow(() -> DataValidator.checkName(testName));
            };
        @Test
        @DisplayName("Check that an error is thrown if name is null")
        void testNullName(){
            // Arrange
            String testName = null;
            // Act
            // Assert
            assertThrows(IllegalArgumentException.class, () -> DataValidator.checkName(testName));
        };
    }

    @Nested
    @DisplayName("Phone Number Tests")
    class PhoneNumberTests {
        @Test
        @DisplayName("Check that an error is not thrown if phone number is valid")
        void testValidPhoneNumber(){
            // Arrange
            String testPhoneNumber = "testPhoneNumber";
            // Act
            // Assert
            assertDoesNotThrow(() -> DataValidator.checkPhoneNumber(testPhoneNumber));
        };
        @Test
        @DisplayName("Check that an error is thrown if phone number is null")
        void testNullPhoneNumber(){
            // Arrange
            String testPhoneNumber = null;
            // Act
            // Assert
            assertThrows(IllegalArgumentException.class, () -> DataValidator.checkPhoneNumber(testPhoneNumber));
        };
    }
}
