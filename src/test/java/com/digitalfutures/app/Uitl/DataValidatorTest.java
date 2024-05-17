package com.digitalfutures.app.Uitl;

import com.digitalfutures.app.Util.DataValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataValidatorTest {
    @Nested
    @DisplayName("Name Tests")
    class nameTests {
        @Test
        @DisplayName("Check that an error is not thrown if name is valid")
        void testValidName(){
            // Arrange
            String testName = "testName";
            // Act
            // Assert
            assertDoesNotThrow(() -> DataValidator.checkName(testName));
        }
        @Test
        @DisplayName("Check that an error is thrown if name is null")
        void testNullName(){
            // Arrange
            String testName = null;
            // Act
            // Assert
            assertThrows(IllegalArgumentException.class, () -> DataValidator.checkName(testName));
        }
    }

    @Nested
    @DisplayName("Phone Number Tests")
    class PhoneNumberTests {
        @Test
        @DisplayName("Check that an error is not thrown if phone number is valid")
        void testValidPhoneNumber(){
            // Arrange
            String testPhoneNumber = "07000000000";
            // Act
            // Assert
            assertDoesNotThrow(() -> DataValidator.checkPhoneNumber(testPhoneNumber));
        }
        @Test
        @DisplayName("Check that an error is thrown if phone number is null")
        void testNullPhoneNumber(){
            // Arrange
            String testPhoneNumber = null;
            // Act
            // Assert
            assertThrows(IllegalArgumentException.class, () -> DataValidator.checkPhoneNumber(testPhoneNumber));
        }
        @Test
        @DisplayName("Check that an error is thrown if phone number is not in a valid format")
        void testInvalidPhoneNumber(){
            // Arrange
            String testPhoneNumber = "Number";
            // Act
            // Assert
            assertThrows(IllegalArgumentException.class, () -> DataValidator.checkPhoneNumber(testPhoneNumber));
        }
    }

    @Nested
    @DisplayName("Email Tests")
    class EmailTests {
        @Test
        @DisplayName("Check that an error is not thrown if email is valid")
        void testValidEmail(){
            // Arrange
            String testEmail = "testemail@email.co.uk";
            // Act
            // Assert
            assertDoesNotThrow(() -> DataValidator.checkEmail(testEmail));
        }
        @Test
        @DisplayName("Check that an error is thrown if email is null")
        void testNullPhoneNumber(){
            // Arrange
            String testEmail = null;
            // Act
            // Assert
            assertThrows(IllegalArgumentException.class, () -> DataValidator.checkEmail(testEmail));
        }
        @Test
        @DisplayName("Check that an error is thrown if email is not in a valid format")
        void testInvalidPhoneNumber(){
            // Arrange
            String testEmail = "Email";
            // Act
            // Assert
            assertThrows(IllegalArgumentException.class, () -> DataValidator.checkEmail(testEmail));
        }
    }
}
