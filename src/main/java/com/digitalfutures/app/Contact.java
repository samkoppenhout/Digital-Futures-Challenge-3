package com.digitalfutures.app;

import com.digitalfutures.app.Util.DataValidator;

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

    private String name;
    private String phoneNumber;
    private String email;

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
