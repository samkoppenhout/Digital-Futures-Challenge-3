package com.digitalfutures.app.Util;
import java.util.Scanner;

public class ConsoleIO {
    private final Scanner scanner = new Scanner(System.in);;
    // Wrapper for input
    public String takeInput() {
        return scanner.nextLine();
    }

    // Wrapper for console print
    public void print(String string) {
        System.out.printf(string);
    }
}