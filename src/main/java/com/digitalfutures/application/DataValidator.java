package com.digitalfutures.application;

public class DataValidator {
    static String checkName(String name) {
        if (name != null) {
            return name;
        } else {throw new IllegalArgumentException("Name Invalid"); }
    }
    static String checkPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            return phoneNumber;
        } else {throw new IllegalArgumentException("Phone Number Invalid"); }
    }
}
