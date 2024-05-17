package com.digitalfutures.application;

import com.digitalfutures.app.AddressBook;
import com.digitalfutures.app.UserInterface;
import com.digitalfutures.app.Util.ConsoleIO;

public class App {
    public static void main(String[] args) {
        ConsoleIO consoleIO = new ConsoleIO();
        AddressBook addressBook = new AddressBook();
        UserInterface userInterface = new UserInterface(consoleIO, addressBook);
        userInterface.start();
    }
}
