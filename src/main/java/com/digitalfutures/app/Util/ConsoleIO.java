package com.digitalfutures.app.Util;
import java.util.Scanner;

public class ConsoleIO {
    private final Scanner scanner = new Scanner(System.in);;

    public String takeInput() {
        return scanner.nextLine();
    }

    public void print(String string) {
        System.out.printf(string);
    }

    public void print(String string, Object... args) {
        System.out.printf(string, args);
    }
}