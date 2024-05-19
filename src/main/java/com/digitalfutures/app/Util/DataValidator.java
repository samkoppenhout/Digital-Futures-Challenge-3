package com.digitalfutures.app.Util;

import java.util.regex.*;

public class DataValidator {
    // Defining regex for phone number and email
    static final String phoneNumberRegex = "^(?:(?:\\+|00)44|0)7\\d{9}$";
    static final Pattern phoneNumberPattern = Pattern.compile(phoneNumberRegex);
    static final String emailRegex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
    static final Pattern emailPattern = Pattern.compile(emailRegex);

    // Check that the name is not empty
    public static String checkName(String name) {
        if (name != null && !name.isEmpty()) {
            return name;
        } else {throw new IllegalArgumentException("Name invalid. "); }
    }

    // Check that the phone number matches the regex
    public static String checkPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            return phoneNumberRegEx(phoneNumber);
        } else {throw new IllegalArgumentException("Phone Number Invalid. "); }
    }

    // Check that the email matches the regex
    public static String checkEmail(String email) {
        if (email != null) {
            return emailRegex(email);
        } else {throw new IllegalArgumentException("Email Invalid. "); }
    }

    // Space removing function to check phone number
    private static String removeSpaces(String string) {
        return string.replaceAll(" ", "");
    }

    // Regex comparison for phone number
    private static String phoneNumberRegEx(String phoneNumber) {
        Matcher matcher = phoneNumberPattern.matcher(removeSpaces(phoneNumber));
        if (matcher.matches()) {
            return removeSpaces(phoneNumber);
        } else { throw new IllegalArgumentException("Phone number invalid. "); }
    }

    // Regex comparison for email
    private static String emailRegex(String email) {
        Matcher matcher = emailPattern.matcher(email);
        if (matcher.matches()) {
            return email;
        } else { throw new IllegalArgumentException("Email invalid. "); }
    }
}
