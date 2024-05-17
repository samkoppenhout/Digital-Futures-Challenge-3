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
}