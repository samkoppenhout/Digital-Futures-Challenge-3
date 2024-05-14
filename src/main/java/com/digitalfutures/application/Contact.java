package com.digitalfutures.application;

public class Contact {
    public void setName(String name) {
        this.name = DataValidator.checkName(name);
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = DataValidator.checkPhoneNumber(phoneNumber);
    }
    public void setEmail(String email) {
        this.email = DataValidator.checkEmail(email);
    }

    protected String name;
    protected String phoneNumber;
    protected String email;

    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }

    public Contact(String name, String phoneNumber, String email){
        this.name = DataValidator.checkName(name);
        this.phoneNumber = DataValidator.checkPhoneNumber(phoneNumber);
        this.email = DataValidator.checkEmail(email);
    }
}
